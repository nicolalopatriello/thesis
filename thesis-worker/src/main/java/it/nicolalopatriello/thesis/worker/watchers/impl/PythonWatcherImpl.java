package it.nicolalopatriello.thesis.worker.watchers.impl;

import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.worker.watchers.Watcher;

import java.io.File;
import java.util.List;

public class PythonWatcherImpl implements Watcher {
    @Override
    public WatcherResponse run(File folder, Recipe.Item item) {
        return null;
    }

    public static class WatcherResponse {
        private boolean success;
        private String commitSha;
        private List<Dependency> dependencies;
        private List<Metric> metrics;
    }

    public static class Dependency {
        private String name;
        private String version;
    }

    public static class Metric {
        private String key;
        private Object value;
    }
}
