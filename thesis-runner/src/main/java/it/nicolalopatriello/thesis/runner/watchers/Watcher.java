package it.nicolalopatriello.thesis.runner.watchers;

import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.runner.watchers.impl.WatcherArgs;

import java.io.File;

public interface Watcher<E extends WatcherArgs> {
    default WatcherResponse run(File folder, Recipe.Item item) {
        return run(folder, convert(item.getArgs()));
    }

    WatcherResponse run(File folder, E args);
    E convert(Object args);
}
