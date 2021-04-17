package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.dto.UserTestDepGitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.entities.UserTestDepGitraceEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTestDepGitraceRepository extends PagingAndSortingWithSpecificationRepository<UserTestDepGitraceEntity, Long> {
    Optional<UserTestDepGitrace> findByUrlAndGitraceId(String repoUrl, Long gitraceId);
}
