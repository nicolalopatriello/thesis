package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateRequest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateResponse;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestWithDep;
import it.nicolalopatriello.thesis.core.service.GitraceService;
import it.nicolalopatriello.thesis.core.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/user-test")
public class UserTestController {

    @Autowired
    private UserTestService userTestService;

    @ThesisAuthorization
    @PostMapping(value = "/")
    @ResponseBody
    public UserTestCreateResponse create(JwtUser user, @Valid @RequestBody UserTestCreateRequest request) throws DuplicateEntityException, BadRequestException, UnauthorizedException, IOException {
        return userTestService.create(user, request);
    }

    @GetMapping(value = "/")
    @ResponseBody
    @ThesisAuthorization
    public List<UserTest> findAll(JwtUser user) throws UnauthorizedException {
        return userTestService.findAll(user);
    }

    @GetMapping(value = "/{userTestId}/")
    @ResponseBody
    @ThesisAuthorization
    public UserTestWithDep findAll(JwtUser user, @Valid @PathVariable Long userTestId) throws UnauthorizedException {
        return userTestService.findByID(userTestId);
    }
}