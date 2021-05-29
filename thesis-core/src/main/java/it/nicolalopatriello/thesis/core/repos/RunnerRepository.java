package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.entities.RunnerEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunnerRepository extends PagingAndSortingWithSpecificationRepository<RunnerEntity, Long> {
    Optional<RunnerEntity> findBySecret(String secret);
}
