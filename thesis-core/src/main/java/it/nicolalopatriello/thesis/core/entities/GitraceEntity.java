package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "gitrace", schema = Schema.SCHEMA_NAME)
@Data
public class GitraceEntity implements WithDTO<Gitrace> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column(name = "git_repo_url")
    private String gitRepoUrl;

    @Column(name = "git_description")
    private String gitDescription;

    @NotNull
    @Column(name = "git_provider")
    private GitProvider gitProvider;

    @Column(name = "connection_id")
    private Long connectionId;

    @Column(name = "last_repo_update")
    private Timestamp lastRepoUpdate;

    @Column(name = "registration_time")
    private Timestamp registrationTime;


    @Override
    public Gitrace dto() {
        Gitrace gitrace = new Gitrace();
        gitrace.setId(id);
        gitrace.setGitRepoUrl(gitRepoUrl);
        gitrace.setGitProvider(gitProvider);
        gitrace.setConnectionId(connectionId);
        gitrace.setLastRepoUpdate(lastRepoUpdate);
        gitrace.setGitDescription(gitDescription);
        gitrace.setRegistrationTime(registrationTime);
        return gitrace;
    }

    public static class Specification extends SimpleSearchSpecification<GitraceEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}