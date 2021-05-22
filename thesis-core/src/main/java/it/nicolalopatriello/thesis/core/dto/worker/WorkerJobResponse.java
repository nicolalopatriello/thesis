package it.nicolalopatriello.thesis.core.dto.worker;

import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkerJobResponse {
     WatcherType watcherType;
    String repositoryUrl;
    String repositoryUsername;
    String repositoryPassword;
    String repositoryBranch;

    public static WorkerJobResponse from(RepositoryEntity repositoryEntity) {
        WorkerJobResponse w = new WorkerJobResponse();
        w.setWatcherType(WatcherType.PYTHON_DEPENDENCY);
        w.setRepositoryUrl(repositoryEntity.getUrl());
        w.setRepositoryUsername(repositoryEntity.getUsername());
        w.setRepositoryPassword(repositoryEntity.getPassword());
        w.setRepositoryBranch(repositoryEntity.getBranch());
        return w;
    }
}
