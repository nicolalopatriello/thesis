package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.LastRepoUpdate;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.repos.GitraceRepository;
import lombok.extern.log4j.Log4j;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j
public class GitraceServiceImpl implements GitraceService {

    @Autowired
    private GitraceRepository repository;


    @Override
    public Gitrace create(GitraceCreateRequest gitraceCreateRequest) throws UnauthorizedException, DuplicateEntityException, IOException, BadRequestException, GitLabApiException {
        if (repository.findByGitRepoUrl(gitraceCreateRequest.getGitRepoUrl()).isPresent())
            throw new DuplicateEntityException(gitraceCreateRequest.getGitRepoUrl());

        GitraceEntity entity = new GitraceEntity();
        entity.setGitRepoUrl(gitraceCreateRequest.getGitRepoUrl());
        entity.setGitProvider(gitraceCreateRequest.getGitProvider());
        entity.setGitDescription(gitraceCreateRequest.getGitDescription());
        entity.setRegistrationTime(new Timestamp(System.currentTimeMillis()));
        Gitrace gitrace = entity.dto();
        LastRepoUpdate lastRepoUpdate = new LastRepoUpdate(gitrace);

        if (lastRepoUpdate.getTimestamp() == null)
            throw new BadRequestException();

        entity.setLastRepoUpdate(lastRepoUpdate.getTimestamp());
        Gitrace dto = repository.save(entity).dto();
        return dto;
    }

    @Override
    public List<Gitrace> findAll() {
        return repository.findAll().stream().map(GitraceEntity::dto).collect(Collectors.toList());
    }
}
