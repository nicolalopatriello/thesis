package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.entities.MetricEntity;
import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricRepository extends PagingAndSortingWithSpecificationRepository<MetricEntity, Long> {
    List<MetricEntity> findByRepositoryId(Long repositoryId);
}
