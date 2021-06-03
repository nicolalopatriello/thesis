package it.nicolalopatriello.thesis.common.dto;

import it.nicolalopatriello.thesis.common.utils.WatcherType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metric {
    private Long id;
    private Severity severity;
    private String description;
    private Long timestamp;
    private Long repositoryId;
    private WatcherType watcherType;

    public Metric() {}

    public Metric(String value, Severity severity, String description) {
        this.severity = severity;
        this.description = description;
    }

    public static enum Severity {
        LOW,
        MEDIUM,
        HIGH
    }
}
