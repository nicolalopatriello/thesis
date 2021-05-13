package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitraceCreateRequest;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.repos.GitraceRepository;
import it.nicolalopatriello.thesis.core.repos.NotificationRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    @Override
    public List<Notification> findByUsername(String username) {
        return notificationRepository.findByUsername(username);
    }
}
