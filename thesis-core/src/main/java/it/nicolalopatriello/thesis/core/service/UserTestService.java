package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateRequest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateResponse;

import java.io.IOException;
import java.util.List;

public interface UserTestService {
    List<UserTest> findAll();
    UserTestCreateResponse create(JwtUser user, UserTestCreateRequest userTestCreateRequest) throws UnauthorizedException, DuplicateEntityException;
}
