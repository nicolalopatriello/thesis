package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.dto.WithDTO;
import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "runner", schema = Schema.SCHEMA_NAME)
@Data
public class RunnerEntity implements WithDTO<Runner> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String secret;

    @Column(name = "registered_at")
    private Timestamp registeredAt;


    @Override
    public Runner dto() {
        Runner runner = new Runner();
        runner.setId(id);
        runner.setSecret(secret);
        if (registeredAt != null)
            runner.setRegisteredAt(registeredAt.getTime());
        return runner;
    }

    public static class Specification extends SimpleSearchSpecification<RunnerEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}