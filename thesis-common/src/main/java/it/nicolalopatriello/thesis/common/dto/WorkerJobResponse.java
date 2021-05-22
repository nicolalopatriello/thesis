package it.nicolalopatriello.thesis.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkerJobResponse {
    private Long repositoryId;
    private String repositoryUrl;
    private String repositoryBranch;
    private RepositoryCredentials credentials;
    private Recipe recipe;

    @Getter
    @Setter
    public static class RepositoryCredentials {
        private String repositoryUsername;
        private String repositoryPassword;
    }
}
