package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.core.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Repository extends DTO {
    private Long id;
    private String url;
    private String username;
    private String password;
    private String branch;
    private String lastCommitSha;
    private Long workerId;
    private Long workerStartedAt;
    private Long workerFinishedAt;
    private Object recipe;
    private Long minutesWatchersInterval;
    private String owner;

    public RepositoryEntity to() {
        RepositoryEntity repositoryEntity = new RepositoryEntity();
        repositoryEntity.setId(id);
        repositoryEntity.setUrl(url);
        repositoryEntity.setUsername(username);
        repositoryEntity.setPassword(password);
        repositoryEntity.setBranch(branch);
        repositoryEntity.setLastCommitSha(lastCommitSha);
        repositoryEntity.setWorkerId(workerId);
        if (workerStartedAt != null)
            repositoryEntity.setWorkerStartedAt(new Timestamp(workerStartedAt));
        if (workerFinishedAt != null)
            repositoryEntity.setWorkerFinishedAt(new Timestamp(workerFinishedAt));
        if (recipe != null)
            repositoryEntity.setRecipe(recipe.toString());
        repositoryEntity.setMinutesWatchersInterval(minutesWatchersInterval);
        repositoryEntity.setOwner(owner);
        return repositoryEntity;
    }


    public static Repository from(RepositoryEntity repositoryEntity) {
        Repository dto = new Repository();
        dto.setId(repositoryEntity.getId());
        dto.setUrl(repositoryEntity.getUrl());
        dto.setUsername(repositoryEntity.getUsername());
        dto.setPassword(repositoryEntity.getPassword());
        dto.setBranch(repositoryEntity.getBranch());
        dto.setLastCommitSha(repositoryEntity.getLastCommitSha());
        dto.setWorkerId(repositoryEntity.getWorkerId());
        if (repositoryEntity.getWorkerStartedAt() != null)
            dto.setWorkerStartedAt(repositoryEntity.getWorkerStartedAt().getTime());
        if (repositoryEntity.getWorkerFinishedAt() != null)
            dto.setWorkerFinishedAt(repositoryEntity.getWorkerFinishedAt().getTime());
        dto.setRecipe(repositoryEntity.getRecipe());
        dto.setMinutesWatchersInterval(repositoryEntity.getMinutesWatchersInterval());
        dto.setOwner(repositoryEntity.getOwner());
        return dto;
    }

}
