package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.entities.SchedulerHistoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GitraceRepository extends PagingAndSortingWithSpecificationRepository<GitraceEntity, Long> {
    Optional<Gitrace> findByGitRepoUrl(String repoUrl);

}
