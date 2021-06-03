package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.dto.Metric;
import it.nicolalopatriello.thesis.common.utils.WatcherType;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.WithDTO;
import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "metric", schema = Schema.SCHEMA_NAME)
@Data
public class MetricEntity implements WithDTO<Metric> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    private Metric.Severity severity;

    @Column
    private String description;

    @Column
    private Timestamp timestamp;

    @Column(name = "watcher_source")
    private WatcherType watcherSource;

    @Column
    private Long repositoryId;

    @Override
    public Metric dto() {
        Metric metric = new Metric();
        metric.setId(id);
        metric.setSeverity(severity);
        metric.setDescription(description);
        if (timestamp != null)
            metric.setTimestamp(timestamp.getTime());
        metric.setWatcherType(watcherSource);
        metric.setRepositoryId(repositoryId);
        return metric;
    }

    public static class Specification extends SimpleSearchSpecification<MetricEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}