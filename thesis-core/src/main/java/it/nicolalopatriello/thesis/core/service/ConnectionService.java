package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.connection.ConnectionCreateRequest;
import it.nicolalopatriello.thesis.core.dto.connection.ConnectionLight;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateRequest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateResponse;

import java.util.List;

public interface ConnectionService {
    List<ConnectionLight> findAll(JwtUser user);
    Connection create(JwtUser user, ConnectionCreateRequest connectionCreateRequest) throws UnauthorizedException, BadRequestException, DuplicateEntityException;
}
