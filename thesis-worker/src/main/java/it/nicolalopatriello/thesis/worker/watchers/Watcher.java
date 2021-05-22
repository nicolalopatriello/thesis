package it.nicolalopatriello.thesis.worker.watchers;

import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.worker.watchers.impl.PythonWatcherImpl;

import java.io.File;

public interface Watcher {
    PythonWatcherImpl.WatcherResponse run(File folder, Recipe.Item item);
}
