package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Dependency extends DTO {
    private Long id;
    private String name;
    private String version;
    private String programmingLanguage;
    private Long repositoryId;

    public DependencyEntity to() {
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setId(id);
        dependencyEntity.setName(name);
        dependencyEntity.setVersion(version);
        dependencyEntity.setProgrammingLanguage(programmingLanguage);
        dependencyEntity.setRepositoryId(repositoryId);
        return dependencyEntity;
    }


    public static Dependency from(DependencyEntity dependencyEntity) {
        Dependency dto = new Dependency();
        dto.setId(dependencyEntity.getId());
        dto.setName(dependencyEntity.getName());
        dto.setVersion(dependencyEntity.getVersion());
        dto.setProgrammingLanguage(dependencyEntity.getProgrammingLanguage());
        dto.setRepositoryId(dependencyEntity.getRepositoryId());
        return dto;
    }

}
