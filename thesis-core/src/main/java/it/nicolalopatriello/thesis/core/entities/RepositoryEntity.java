package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.WithDTO;
import it.nicolalopatriello.thesis.core.dto.repository.Repository;
import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "repository", schema = Schema.SCHEMA_NAME)
@Data

public class RepositoryEntity implements WithDTO<Repository> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    private String url;

    @NotNull
    @Column
    private String username;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private String branch;

    @Column(name = "last_commit_sha")
    private String lastCommitSha;

    @Column(name = "runner_id")
    private Long runnerId;

    @Column(name = "runner_started_at")
    private Timestamp runnerStartedAt;

    @Column(name = "runner_finished_at")
    private Timestamp runnerFinishedAt;

    @Column
    private String recipe;

    @NotNull
    @Column(name = "minutes_watchers_interval")
    private Long minutesWatchersInterval;

    @NotNull
    @Column
    private String owner;

    @Override
    public Repository dto() {
        Repository repository = new Repository();
        repository.setId(id);
        repository.setUrl(url);
        repository.setUsername(username);
        repository.setPassword(password);
        repository.setBranch(branch);
        repository.setLastCommitSha(lastCommitSha);
        repository.setRunnerId(runnerId);
        if (runnerStartedAt != null)
            repository.setRunnerStartedAt(runnerStartedAt.getTime());
        if (runnerFinishedAt != null)
            repository.setRunnerFinishedAt(runnerFinishedAt.getTime());
        repository.setRecipe(recipe);
        repository.setMinutesWatchersInterval(minutesWatchersInterval);
        repository.setOwner(owner);
        return repository;
    }

    public static class Specification extends SimpleSearchSpecification<RepositoryEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}
