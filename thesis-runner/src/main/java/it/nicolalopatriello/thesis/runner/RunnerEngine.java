package it.nicolalopatriello.thesis.runner;


import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.exception.FolderCreationException;
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

    public RunnerResponse accept(RunnerJobResponse job) {
        String sha = updateCode(job);
        List<WatcherResponse> output = Lists.newLinkedList();

        if (!job.getLastCommitSha().equals(sha)) {
            for (Recipe.Item item : job.getRecipe().getItems()) {
                try {
                    Watcher<?> w = WatcherFactory.get(item.getWatcherType());
                    File folder = folder(job);
                    WatcherResponse response = w.run(new File(folder, extractName(job.getRepositoryUrl())), item);
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

    private String updateCode(RunnerJobResponse worker) {
        File f = folder(worker);
        if (!f.exists() && !f.mkdirs())
            throw new FolderCreationException(f);
        String latestSha = LocalRepoHandler.getSha(f, worker.getRepositoryUrl(), worker.getRepositoryBranch(), worker.getCredentials());
        return latestSha;
    }

    private File folder(RunnerJobResponse worker) {
        return new File(basePath, "repo-" + worker.getRepositoryId());
    }


}
