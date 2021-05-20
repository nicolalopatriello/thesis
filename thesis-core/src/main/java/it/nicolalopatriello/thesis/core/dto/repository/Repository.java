package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.common.spring.dto.DTO;
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
    private Timestamp lastPullTimestamp;
    private String lastCommitSha;
    private String owner;

    public RepositoryEntity to() {
        RepositoryEntity repositoryEntity = new RepositoryEntity();
        repositoryEntity.setId(id);
        repositoryEntity.setUrl(url);
        repositoryEntity.setUsername(username);
        repositoryEntity.setPassword(password);
        repositoryEntity.setBranch(branch);
        repositoryEntity.setLastPullTimestamp(lastPullTimestamp);
        repositoryEntity.setLastCommitSha(lastCommitSha);
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
        dto.setLastPullTimestamp(repositoryEntity.getLastPullTimestamp());
        dto.setOwner(repositoryEntity.getOwner());
        return dto;
    }

}
