package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.User;
import it.nicolalopatriello.thesis.core.dto.users.LoginRequest;
import it.nicolalopatriello.thesis.core.dto.users.LoginResponse;
import it.nicolalopatriello.thesis.core.dto.users.UserCreateRequest;
import it.nicolalopatriello.thesis.core.dto.users.UserCreateResponse;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import it.nicolalopatriello.thesis.core.exception.BadUserCreationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(String id);

    Page<User> findAll(Specification<UserEntity> specification, Pageable pageable);

    LoginResponse login(LoginRequest authenticationRequest) throws UnauthorizedException;

    UserCreateResponse create(UserCreateRequest userCreateRequest) throws BadUserCreationException;
}
