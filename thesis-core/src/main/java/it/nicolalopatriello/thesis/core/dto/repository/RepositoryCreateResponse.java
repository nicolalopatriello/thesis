package it.nicolalopatriello.thesis.core.dto.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositoryCreateResponse {
    private String url;
    private String branch;
}
