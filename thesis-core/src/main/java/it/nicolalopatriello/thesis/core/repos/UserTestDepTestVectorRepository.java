package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.dto.UserTestDepGitrace;
import it.nicolalopatriello.thesis.core.dto.UserTestDepTestVector;
import it.nicolalopatriello.thesis.core.entities.UserTestDepGitraceEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestDepTestVectorEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTestDepTestVectorRepository extends PagingAndSortingWithSpecificationRepository<UserTestDepTestVectorEntity, Long> {
    Optional<UserTestDepTestVector> findByUrlAndTestVectorId(String repoUrl, Long testVectorId);
}
