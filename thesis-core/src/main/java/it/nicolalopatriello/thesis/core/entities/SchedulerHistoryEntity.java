package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistory;
import it.nicolalopatriello.thesis.core.dto.DepType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "scheduler_history", schema = Schema.SCHEMA_NAME)
@Data
public class SchedulerHistoryEntity implements WithDTO<SchedulerHistory> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    private DepType type;

    @Column
    private Timestamp timestamp;


    @Override
    public SchedulerHistory dto() {
        SchedulerHistory sh = new SchedulerHistory();
        sh.setId(id);
        sh.setType(type);
        sh.setTimestamp(timestamp);
        return sh;
    }

    public static class Specification extends SimpleSearchSpecification<SchedulerHistoryEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}