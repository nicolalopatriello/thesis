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

    @Column(name = "worker_id")
    private Long workerId;

    @Column(name = "worker_started_at")
    private Timestamp workerStartedAt;

    @Column(name = "worker_finished_at")
    private Timestamp workerFinishedAt;

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
        repository.setWorkerId(workerId);
        if (workerStartedAt != null)
            repository.setWorkerStartedAt(workerStartedAt.getTime());
        if (workerFinishedAt != null)
            repository.setWorkerFinishedAt(workerFinishedAt.getTime());
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
