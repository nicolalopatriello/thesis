package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;
import it.nicolalopatriello.thesis.core.service.GitraceService;
import it.nicolalopatriello.thesis.core.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/")
    @ResponseBody
    @ThesisAuthorization
    public List<Notification> findAll(JwtUser user) throws DuplicateEntityException, UnauthorizedException, IOException {
        return notificationService.findByUsername(user.getUsername());
    }
}