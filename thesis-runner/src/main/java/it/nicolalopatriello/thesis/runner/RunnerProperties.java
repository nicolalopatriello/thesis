package it.nicolalopatriello.thesis.runner;

import java.io.File;
import java.util.Optional;

public class RunnerProperties {
    private final long awaitInterval;
    private final File basePath;

    private static RunnerProperties instance;

    public static RunnerProperties getInstance() {
        if (instance == null)
            instance = new RunnerProperties();
        return instance;
    }

    private RunnerProperties() {
        this.basePath = new File(getOrElse("RUNNER_BASE_PATH", "/tmp/runner"));
        this.awaitInterval = Long.parseLong(getOrElse("RUNNER_WAIT_INTERVAL", "5000"));
    }

    public static File basePath() {
        return getInstance().basePath;
    }

    public static long awaitInterval() {
        return getInstance().awaitInterval;
    }

    private String getOrElse(String key, String value) {
        return Optional.ofNullable(System.getenv(key)).orElse(value);
    }

}
