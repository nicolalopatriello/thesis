package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.connection.ConnectionCreateRequest;
import it.nicolalopatriello.thesis.core.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/connection")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @ThesisAuthorization
    @PostMapping(value = "/")
    @ResponseBody
    public Connection create(JwtUser user, @Valid @RequestBody ConnectionCreateRequest request) throws DuplicateEntityException, BadRequestException, UnauthorizedException, IOException {
        return connectionService.create(user, request);
    }


    @GetMapping(value = "/")
    @ResponseBody
    @ThesisAuthorization
    public List<Connection> findAll(JwtUser user) throws DuplicateEntityException, UnauthorizedException, IOException {
        return connectionService.findAll(user);
    }
}