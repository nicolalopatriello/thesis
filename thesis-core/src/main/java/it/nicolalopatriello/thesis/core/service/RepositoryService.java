package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.core.utils.JwtUser;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;

public interface RepositoryService {
    RepositoryCreateResponse create(JwtUser user, RepositoryCreateRequest repositoryCreateRequest) throws BadRequestException;
}
