package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistory;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@ToString
@Entity
@Table(name = "user_test", schema = Schema.SCHEMA_NAME)
@Getter
@Setter
public class UserTestEntity implements WithDTO<UserTest> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column(name = "git_repo_url")
    private String gitRepoUrl;

    @NotNull
    @Column(name = "git_provider")
    private GitProvider gitProvider;

    @Column
    private String description;

    @Column(name = "connection_id")
    private Long connectionId;

    @Column
    private String username;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Override
    public UserTest dto() {
        UserTest userTest = new UserTest();
        userTest.setGitRepoUrl(gitRepoUrl);
        userTest.setGitProvider(gitProvider);
        userTest.setDescription(description);
        userTest.setConnectionId(connectionId);
        userTest.setUsername(username);
        userTest.setCreatedAt(createdAt);
        userTest.setId(id);
        return userTest;
    }

    public static class Specification extends SimpleSearchSpecification<UserTestEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }

}
