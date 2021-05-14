package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import org.gitlab4j.api.GitLabApiException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GitraceService {
    List<Gitrace> findAll();
    Gitrace create(GitraceCreateRequest gitraceCreateRequest) throws UnauthorizedException, DuplicateEntityException, BadRequestException, IOException, GitLabApiException;
    Optional<Date> getLatestCommit(Gitrace gitrace) throws IOException, GitLabApiException;
}
