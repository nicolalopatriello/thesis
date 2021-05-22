package it.nicolalopatriello.thesis.worker;


import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import it.nicolalopatriello.thesis.worker.exception.FolderCreationException;
import it.nicolalopatriello.thesis.worker.exception.InvalidUrlException;
import it.nicolalopatriello.thesis.worker.watchers.Watcher;
import it.nicolalopatriello.thesis.worker.watchers.WatcherFactory;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.util.List;

@Log4j
public class WorkerEngine {
    public static final String GIT = ".git";
    private final File basePath = RunnerProperties.basePath();

    public RunnerResponse accept(WorkerJobResponse worker) {
        String sha = updateCode(worker);
        List<WatcherResponse> output = Lists.newLinkedList();

        for (Recipe.Item item : worker.getRecipe().getItems()) {
            try {
                Watcher<?> w = WatcherFactory.get(item.getWatcherType());
                File folder = folder(worker);
                WatcherResponse response = w.run(new File(folder, extractName(worker.getRepositoryUrl())), item);
                output.add(response);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                output.add(WatcherResponse.fail());
            }
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

    private String updateCode(WorkerJobResponse worker) {
        File f = folder(worker);
        if (!f.exists() && !f.mkdirs())
            throw new FolderCreationException(f);

        //return sha;
        return null;
    }

    private File folder(WorkerJobResponse worker) {
        return new File(basePath, "repo-" + worker.getRepositoryId());
    }


}
