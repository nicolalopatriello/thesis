package it.nicolalopatriello.thesis.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkerJobResponse {
    private Repository repository;
    private Recipe recipe;

    @Getter
    @Setter
    public static class Repository {
        private String repositoryUrl;
        private String repositoryUsername;
        private String repositoryPassword;
        private String repositoryBranch;
    }
}
