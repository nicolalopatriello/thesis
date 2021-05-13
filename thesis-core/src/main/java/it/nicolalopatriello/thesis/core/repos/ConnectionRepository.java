package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends PagingAndSortingWithSpecificationRepository<ConnectionEntity, Long> {
    List<Connection> findByUsername(String username);
}
