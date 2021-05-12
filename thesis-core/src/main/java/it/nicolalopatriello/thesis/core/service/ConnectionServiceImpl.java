package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.connection.ConnectionCreateRequest;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import it.nicolalopatriello.thesis.core.repos.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    ConnectionRepository connectionRepository;


    @Override
    public List<Connection> findAll(JwtUser user) {
        return connectionRepository.findByUsername(user.getUsername());
    }

    @Override
    public Connection create(JwtUser user, ConnectionCreateRequest connectionCreateRequest) throws UnauthorizedException, BadRequestException, DuplicateEntityException {
        ConnectionEntity entity = new ConnectionEntity();
        entity.setGitProvider(connectionCreateRequest.getGitProvider());
        entity.setEndpoint(connectionCreateRequest.getEndpoint());
        entity.setToken(connectionCreateRequest.getToken());
        entity.setUsername(user.getUsername());
        Connection dto = connectionRepository.save(entity).dto();
        return dto;
    }
}
