package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisRepositoryRepository extends PagingAndSortingWithSpecificationRepository<RepositoryEntity, Long> {
}
