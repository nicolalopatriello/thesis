package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.connection.ConnectionCreateRequest;
import it.nicolalopatriello.thesis.core.dto.connection.ConnectionLight;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.List;

public interface RepositoryService {
    RepositoryCreateResponse create(JwtUser user, RepositoryCreateRequest repositoryCreateRequest) throws BadRequestException;
}
