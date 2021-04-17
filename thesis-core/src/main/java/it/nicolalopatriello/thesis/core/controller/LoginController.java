package it.nicolalopatriello.thesis.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.users.LoginRequest;
import it.nicolalopatriello.thesis.core.dto.users.LoginResponse;
import it.nicolalopatriello.thesis.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    @ResponseBody
    @ThesisPublicApi
    public LoginResponse login(@Valid @RequestBody LoginRequest authenticationRequest) throws UnauthorizedException {
        return userService.login(authenticationRequest);
    }
}