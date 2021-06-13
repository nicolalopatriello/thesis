package it.nicolalopatriello.thesis.runner;


import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.GitRepoHandler;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.exception.DecryptionException;
import it.nicolalopatriello.thesis.common.exception.FolderCreationException;
import it.nicolalopatriello.thesis.common.utils.DataEncryptor;
import it.nicolalopatriello.thesis.common.utils.ThesisConstant;
import it.nicolalopatriello.thesis.runner.exception.InvalidUrlException;
import it.nicolalopatriello.thesis.runner.watchers.Watcher;
import it.nicolalopatriello.thesis.runner.watchers.WatcherFactory;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.List;

@Log4j
public class RunnerEngine {
    public static final String GIT = ".git";
    private final File basePath = RunnerProperties.basePath();
    private final GitRepoHandler handler = new GitRepoHandler();

    public RunnerResponse accept(RunnerJobResponse job) throws Exception {
        String sha = fetch(job);
        List<WatcherResponse> output = Lists.newLinkedList();

        if (job.getLastCommitSha() == null || !job.getLastCommitSha().equals(sha)) {
            for (Recipe.Item item : job.getRecipe().getItems()) {
                try {
                    Watcher<?> w = WatcherFactory.get(item.getWatcherType());
                    File folder = folder(job);
                    WatcherResponse response = w.run(folder, item, decrypt(job.getCredentials()));
                    output.add(response);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    output.add(WatcherResponse.fail());
                }
            }
        } else {
            log.debug("No changes on code base. Skip job");
        }

        RunnerResponse r = new RunnerResponse();
        r.setCommitSha(sha);
        r.setRepositoryId(job.getRepositoryId());
        r.setWatchers(output);
        return r;
    }

    private String extractName(String url) throws InvalidUrlException {
        url = url.trim();
        if (url.isEmpty() || !url.endsWith(GIT))
            throw new InvalidUrlException(url);
        int s = url.lastIndexOf("/");
        return url.substring(s + 1, url.length() - GIT.length());
    }

    /**
     * @param worker
     * @return sha
     * @throws InvalidUrlException
     */
    private String fetch(RunnerJobResponse worker) throws InvalidUrlException, DecryptionException {
        File f = folder(worker);
        if (!f.exists() && !f.mkdirs())
            throw new FolderCreationException(f);
        try {

            return handler.fetch(f, worker.getRepositoryUrl(), worker.getRepositoryBranch(), decrypt(worker.getCredentials()));
        } catch (Exception e) {
            throw new DecryptionException();
        }
    }

    RunnerJobResponse.RepositoryCredentials decrypt(RunnerJobResponse.RepositoryCredentials credentials) {
        RunnerJobResponse.RepositoryCredentials c = new RunnerJobResponse.RepositoryCredentials();
        try {
            DataEncryptor dataEncryptor = DataEncryptor.from(ThesisConstant.ENCRYPT_SECRET, ThesisConstant.ENCRYPT_SALT);
            c.setRepositoryUsername(dataEncryptor.decrypt(credentials.getRepositoryUsername()));
            c.setRepositoryPassword(dataEncryptor.decrypt(credentials.getRepositoryPassword()));
        } catch (Exception e) {
            log.error("Cannot decrypt credentials" + e.getMessage());
        }
        return c;
    }

    private File folder(RunnerJobResponse worker) throws InvalidUrlException {
        return new File(basePath, "repo-" + worker.getRepositoryId() + "-" + extractName(worker.getRepositoryUrl()));
    }


}
