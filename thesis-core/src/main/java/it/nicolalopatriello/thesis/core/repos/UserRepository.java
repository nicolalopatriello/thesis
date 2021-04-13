package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingWithSpecificationRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}
