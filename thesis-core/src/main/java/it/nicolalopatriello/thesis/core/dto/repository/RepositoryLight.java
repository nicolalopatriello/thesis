package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositoryLight {
    private Long id;
    private String url;
    private String branch;
    private String lastCommitSha;
    private Long runnerId;
    private Long runnerStartedAt;
    private Long runnerFinishedAt;
    private Object recipe;
    private Long minutesWatchersInterval;


    public static RepositoryLight from(RepositoryEntity r) {
        RepositoryLight light = new RepositoryLight();
        light.setId(r.getId());
        light.setUrl(r.getUrl());
        light.setBranch(r.getBranch());
        light.setLastCommitSha(r.getLastCommitSha());
        light.setRunnerId(r.getRunnerId());
        if (r.getRunnerStartedAt() != null)
            light.setRunnerStartedAt(r.getRunnerStartedAt().getTime());
        if (r.getRunnerFinishedAt() != null)
            light.setRunnerFinishedAt(r.getRunnerFinishedAt().getTime());
        light.setRecipe(r.getRecipe());
        light.setMinutesWatchersInterval(r.getMinutesWatchersInterval());
        return light;
    }
}
