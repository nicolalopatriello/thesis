package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.entities.TestVectorEntity;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestVectorRepository extends PagingAndSortingWithSpecificationRepository<TestVectorEntity, String> {
    Optional<TestVectorEntity> findById(Long id);
}
