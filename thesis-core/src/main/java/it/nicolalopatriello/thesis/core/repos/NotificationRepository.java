package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.dto.DepType;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;
import it.nicolalopatriello.thesis.core.entities.NotificationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends PagingAndSortingWithSpecificationRepository<NotificationEntity, Long> {
    List<Notification> findByUsername(String username);
    List<Notification> findByUsernameAndUserTestIdAndChangedDepTypeAndChangedDepId(String username, Long userTestId, DepType changedDepType, Long changedDepId);
}
