package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.exception.UnsopportedGitProviderException;
import it.nicolalopatriello.thesis.core.repos.ConnectionRepository;
import it.nicolalopatriello.thesis.core.repos.GitraceRepository;
import it.nicolalopatriello.thesis.core.utils.Utility;
import lombok.extern.log4j.Log4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
public class GitraceServiceImpl implements GitraceService {

    @Autowired
    private GitraceRepository repository;

    @Autowired
    private ConnectionRepository connectionRepository;


    @Override
    public Gitrace create(GitraceCreateRequest gitraceCreateRequest) throws UnauthorizedException, DuplicateEntityException, IOException, BadRequestException, GitLabApiException {
        if (repository.findByGitRepoUrl(gitraceCreateRequest.getGitRepoUrl()).isPresent())
            throw new DuplicateEntityException(gitraceCreateRequest.getGitRepoUrl());

        GitraceEntity entity = new GitraceEntity();
        entity.setGitRepoUrl(gitraceCreateRequest.getGitRepoUrl());
        entity.setGitProvider(gitraceCreateRequest.getGitProvider());
        entity.setGitDescription(gitraceCreateRequest.getGitDescription());
        entity.setRegistrationTime(new Timestamp(System.currentTimeMillis()));
        entity.setConnectionId(gitraceCreateRequest.getConnectionId());
        Gitrace gitrace = entity.dto();
        Date latestCommit = getLatestCommit(gitrace).orElse(new Date(0L));
        entity.setLastRepoUpdate(new Timestamp(latestCommit.getTime()));
        return repository.save(entity).dto();
    }

    @Override
    public List<Gitrace> findAll() {
        return repository.findAll().stream().map(GitraceEntity::dto).collect(Collectors.toList());
    }

    public Optional<Date> getLatestCommit(Gitrace gitrace) throws IOException, GitLabApiException {
        switch (gitrace.getGitProvider()) {
            case GITHUB:
                return getGitHubDate(gitrace);
            case GITLAB:
                return getGitLabDate(gitrace);
            default:
                throw new UnsopportedGitProviderException(gitrace.getGitProvider());
        }
    }

    private Optional<Date> getGitLabDate(Gitrace gitrace) throws GitLabApiException {
        Optional<ConnectionEntity> c = connectionRepository.findById(gitrace.getConnectionId());
        if (c.isPresent()) {
            Connection conn = c.get().dto();
            GitLabApi gitLabApi = new GitLabApi(conn.getEndpoint(), conn.getToken());
            String projectPath = Utility.getProjectPath(conn.getEndpoint(), gitrace.getGitRepoUrl());
            String defaultBranch = gitLabApi.getProjectApi().getProject(projectPath).getDefaultBranch();
            List<Commit> commits = gitLabApi.getCommitsApi().getCommits(projectPath, defaultBranch, null, null, null, false, false, true);
            if (commits.size() > 0) {
                return Optional.of(commits.get(0).getAuthoredDate());
            }
        }
        return Optional.empty();
    }

    private Optional<Date> getGitHubDate(Gitrace gitrace) throws IOException {
        GitHub github = GitHub.connect();
        //    GitHub github = new GitHubBuilder().withOAuthToken("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").build();
        Optional<String> repoNameOpt = Utility.getGitHubRepoName(gitrace.getGitRepoUrl());
        if (repoNameOpt.isPresent()) {
            GHRepository repo = github.getRepository(repoNameOpt.get());
            PagedIterable<GHCommit> pagedIterable = repo.listCommits();
            Optional<GHCommit> optionalGHCommit = pagedIterable.toList().stream().findFirst();
            if (optionalGHCommit.isPresent()) {
                Date date = optionalGHCommit.get().getCommitDate();
                return Optional.of(date);
            } else {
                //log
            }
        }
        return Optional.empty();
    }
}
