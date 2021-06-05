package it.nicolalopatriello.thesis.common.dto;

import it.nicolalopatriello.thesis.common.utils.WatcherType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Metric {
    private Long id;
    private Severity severity;
    private String description;
    private Long timestamp;
    private Long repositoryId;
    private WatcherType watcherType;


    public Metric(Severity severity, String description, WatcherType watcherType) {
        this.severity = severity;
        this.description = description;
        this.watcherType = watcherType;
    }

    public static enum Severity {
        LOW,
        MEDIUM,
        HIGH,
        UNDEFINED
    }
}
