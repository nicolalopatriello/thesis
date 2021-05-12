package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import it.nicolalopatriello.thesis.core.repos.ConnectionRepository;
import it.nicolalopatriello.thesis.core.utils.Utility;
import lombok.Getter;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Getter
public class LastRepoUpdate {

    private Timestamp timestamp;

    @Autowired
    ConnectionRepository connectionRepository;


    public LastRepoUpdate(Gitrace gitrace) throws IOException, GitLabApiException {
        switch (gitrace.getGitProvider()) {
            case GITHUB:
                GitHub github = GitHub.connect();
                //    GitHub github = new GitHubBuilder().withOAuthToken("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").build();
                Optional<String> repoNameOpt = Utility.getGitHubRepoName(gitrace.getGitRepoUrl());
                if (repoNameOpt.isPresent()) {
                    GHRepository repo = github.getRepository(repoNameOpt.get());
                    PagedIterable<GHCommit> pagedIterable = repo.listCommits();
                    Optional<GHCommit> optionalGHCommit = pagedIterable.toList().stream().findFirst();
                    if (optionalGHCommit.isPresent()) {
                        Date date = optionalGHCommit.get().getCommitDate();
                        this.timestamp = new Timestamp(date.getTime());
                    }
                } else {
                    this.timestamp = null;
                }
                break;
            case GITLAB:
                Optional<ConnectionEntity> c = connectionRepository.findById(gitrace.getConnectionId());
                if (c.isPresent()) {
                    Connection conn = c.get().dto();
                    GitLabApi gitLabApi = new GitLabApi(conn.getEndpoint(), conn.getToken());
                    Project t = gitLabApi.getProjectApi().getProject(gitrace.getGitRepoUrl());
                    System.err.println(t.toString());
                }
                break;
        }
    }
}
