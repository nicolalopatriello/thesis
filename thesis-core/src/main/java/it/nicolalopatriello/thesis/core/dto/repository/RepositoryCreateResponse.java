package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositoryCreateResponse {
    private String url;
    private String branch;

    public static RepositoryCreateResponse from(RepositoryEntity repositoryEntity) {
        RepositoryCreateResponse r = new RepositoryCreateResponse();
        r.setBranch(repositoryEntity.getBranch());
        r.setUrl(repositoryEntity.getUrl());
        return r;
    }

}
