package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepositoryRepository extends PagingAndSortingWithSpecificationRepository<RepositoryEntity, Long> {
    List<RepositoryEntity> findByWorkerIdIsNull();
}
