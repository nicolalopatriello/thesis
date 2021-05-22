package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.Worker;
import it.nicolalopatriello.thesis.core.entities.WorkerEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends PagingAndSortingWithSpecificationRepository<WorkerEntity, Long> {
    Optional<Worker> findBySecret(String secret);
}
