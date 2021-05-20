package it.nicolalopatriello.thesis.core.dto.repository;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RepositoryCreateRequest {
    private String url;
    private String username;
    private String password;
    private String branch;
}
