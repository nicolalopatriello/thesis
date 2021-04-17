package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.entities.SchedulerHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerHistoryRepository extends PagingAndSortingWithSpecificationRepository<SchedulerHistoryEntity, Long> {
}
