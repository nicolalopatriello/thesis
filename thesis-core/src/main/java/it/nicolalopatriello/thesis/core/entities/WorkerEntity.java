package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.Worker;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "worker", schema = Schema.SCHEMA_NAME)
@Data
public class WorkerEntity implements WithDTO<Worker> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String secret;

    @Column(name = "registered_at")
    private Timestamp registeredAt;


    @Override
    public Worker dto() {
        Worker worker = new Worker();
        worker.setId(id);
        worker.setSecret(secret);
        if (registeredAt != null)
            worker.setRegisteredAt(registeredAt.getTime());
        return worker;
    }

    public static class Specification extends SimpleSearchSpecification<WorkerEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}