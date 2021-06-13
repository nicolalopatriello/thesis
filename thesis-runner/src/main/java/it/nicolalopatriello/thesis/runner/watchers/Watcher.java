package it.nicolalopatriello.thesis.runner.watchers;

import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.watchers.impl.WatcherArgs;

import java.io.File;

public interface Watcher<E extends WatcherArgs> {
    default WatcherResponse run(File folder, Recipe.Item item, RunnerJobResponse.RepositoryCredentials credentials) {
        return run(folder, convert(item.getArgs()), credentials);
    }

    WatcherResponse run(File folder, E args, RunnerJobResponse.RepositoryCredentials creds);
    E convert(Object args);
}
