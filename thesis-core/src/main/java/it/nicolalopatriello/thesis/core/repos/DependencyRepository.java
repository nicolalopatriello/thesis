package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependencyRepository extends PagingAndSortingWithSpecificationRepository<DependencyEntity, Long> {
    Optional<DependencyEntity> findByNameAndVersionAndProgrammingLanguageAndRepositoryId(String name, String version, String programmingLanguage, Long repositoryId);

    List<DependencyEntity> findByRepositoryId(Long repositoryId);
}
