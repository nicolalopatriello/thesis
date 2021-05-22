package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.Worker;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import it.nicolalopatriello.thesis.core.entities.WorkerEntity;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends PagingAndSortingWithSpecificationRepository<WorkerEntity, Long> {
    Optional<Worker> findBySecret(String secret);
}
