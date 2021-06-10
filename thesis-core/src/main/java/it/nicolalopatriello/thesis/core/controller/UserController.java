package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.security.JwtUser;
import it.nicolalopatriello.thesis.core.dto.User;
import it.nicolalopatriello.thesis.core.dto.users.UserCreateRequest;
import it.nicolalopatriello.thesis.core.dto.users.UserCreateResponse;
import it.nicolalopatriello.thesis.core.exception.BadUserCreationException;
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

    @PostMapping(value = "/")
    @ResponseBody
    @ThesisPublicApi
    public UserCreateResponse create(@Valid @RequestBody UserCreateRequest userCreateRequest) throws BadUserCreationException {
        return userService.create(userCreateRequest);
    }
}