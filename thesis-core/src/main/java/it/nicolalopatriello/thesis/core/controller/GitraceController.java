package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;
import it.nicolalopatriello.thesis.core.service.GitraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/gitrace")
public class GitraceController {

    @Autowired
    private GitraceService gitraceService;

    @ThesisAuthorization
    @PostMapping(value = "/")
    @ResponseBody
    public Gitrace create(JwtUser user, @Valid @RequestBody GitraceCreateRequest request) throws DuplicateEntityException, BadRequestException, UnauthorizedException, IOException {
        return gitraceService.create(request);
    }


    @GetMapping(value = "/")
    @ResponseBody
    @ThesisAuthorization
    public List<Gitrace> findAll() throws DuplicateEntityException, UnauthorizedException, IOException {
        return gitraceService.findAll();
    }
}