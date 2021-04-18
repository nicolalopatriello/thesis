package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;

import java.io.IOException;
import java.util.List;

public interface NotificationService {
    List<Notification> findByUsername(String username);
}
