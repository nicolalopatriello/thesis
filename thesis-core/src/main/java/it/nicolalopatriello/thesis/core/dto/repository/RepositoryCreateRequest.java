package it.nicolalopatriello.thesis.core.dto.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositoryCreateRequest {
    private String url;
    private String username;
    private String password;
    private String branch;
    private Object recipe;
    private Long minutesWatchersInterval;
}
