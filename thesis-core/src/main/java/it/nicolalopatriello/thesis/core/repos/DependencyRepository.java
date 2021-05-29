package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.jpa.PagingAndSortingWithSpecificationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface DependencyRepository extends PagingAndSortingWithSpecificationRepository<DependencyEntity, Long> {
    Optional<DependencyEntity> findByNameAndVersionAndProgrammingLanguageAndRepositoryId(String name, String version, String programmingLanguage, Long repositoryId);
}
