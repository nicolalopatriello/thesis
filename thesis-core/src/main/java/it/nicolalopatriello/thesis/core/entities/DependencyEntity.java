package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.Dependency;
import it.nicolalopatriello.thesis.core.dto.WithDTO;
import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dependency", schema = Schema.SCHEMA_NAME)
@Data
public class DependencyEntity implements WithDTO<Dependency> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    private String name;

    @Column
    private String version;

    @Column(name = "programming_language")
    private String programmingLanguage;

    @Column
    private Long repositoryId;


    @Override
    public Dependency dto() {
        Dependency dependency = new Dependency();
        dependency.setId(id);
        dependency.setName(name);
        dependency.setVersion(version);
        dependency.setProgrammingLanguage(programmingLanguage);
        dependency.setRepositoryId(repositoryId);
        return dependency;
    }

    public static class Specification extends SimpleSearchSpecification<DependencyEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}