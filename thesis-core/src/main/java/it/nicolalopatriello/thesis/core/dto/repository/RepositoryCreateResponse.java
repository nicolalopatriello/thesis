package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.core.dto.watcher.LightWatcherConfig;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryCreateResponse {
    private String url;
    private String branch;
    private List<LightWatcherConfig> watchers;


    public static RepositoryCreateResponse from(RepositoryEntity repositoryEntity, List<LightWatcherConfig> watchers) {
        RepositoryCreateResponse r = new RepositoryCreateResponse();
        r.setBranch(repositoryEntity.getBranch());
        r.setUrl(repositoryEntity.getUrl());
        r.setWatchers(watchers);
        return r;
    }

}
