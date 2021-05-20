package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.repository.Repository;
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

    @Column
    private String password;

    @NotNull
    @Column
    private String branch;

    @Column(name = "last_pull_timestamp")
    private Timestamp lastPullTimestamp;

    @Column(name = "last_commit_sha")
    private String lastCommitSha;

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
        repository.setLastPullTimestamp(lastPullTimestamp);
        repository.setLastCommitSha(lastCommitSha);
        repository.setOwner(owner);
        return repository;
    }

    public static class Specification extends SimpleSearchSpecification<GitraceEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}
