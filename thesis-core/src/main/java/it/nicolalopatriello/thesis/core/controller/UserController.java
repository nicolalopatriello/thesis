package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.User;
import it.nicolalopatriello.thesis.core.dto.users.LoginRequest;
import it.nicolalopatriello.thesis.core.dto.users.LoginResponse;
import it.nicolalopatriello.thesis.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/self/")
    @ResponseBody
    @ThesisPublicApi
    public User findSelf(JwtUser jwtUser) throws NotFoundException {
        Optional<User> res = userService.findById(jwtUser.getUsername());
        if (res.isPresent()) {
            return res.get();
        } else {
            throw new NotFoundException();
        }
    }
}