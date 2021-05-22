package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.core.dto.watcher.LightWatcherConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryCreateRequest {
    private String url;
    private String username;
    private String password;
    private String branch;
    private List<LightWatcherConfig> watchers;
}
