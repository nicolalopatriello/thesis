package it.nicolalopatriello.thesis.runner;

import java.io.File;
import java.util.Optional;

public class RunnerProperties {
    private final long awaitInterval;
    private final File basePath;
    private final String secret;

    private static RunnerProperties instance;

    public static RunnerProperties getInstance() {
        if (instance == null)
            instance = new RunnerProperties();
        return instance;
    }

    private RunnerProperties() {
        this.basePath = new File(getOrElse("RUNNER_BASE_PATH", "/tmp/runner"));
        this.awaitInterval = Long.parseLong(getOrElse("RUNNER_WAIT_INTERVAL", "50000"));
        this.secret = getOrElse("RUNNER_SECRET", "runner-0-secret");
    }

    public static File basePath() {
        return getInstance().basePath;
    }

    public static long awaitInterval() {
        return getInstance().awaitInterval;
    }

    public static String secret() {
        return getInstance().secret;
    }

    private String getOrElse(String key, String value) {
        return Optional.ofNullable(System.getenv(key)).orElse(value);
    }

}
