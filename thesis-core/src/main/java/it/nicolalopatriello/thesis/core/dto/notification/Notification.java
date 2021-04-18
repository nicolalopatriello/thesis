package it.nicolalopatriello.thesis.core.dto.notification;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.DepType;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.entities.NotificationEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Log4j
@Data
@ToString
public class Notification extends DTO {

    private Long id;
    private Long userTestId;
    private String userTestUrl;
    private DepType changedDepType;
    private Long changedDepId;
    private String uuid;
    private Integer checked;
    private Timestamp createdAt;
    private Timestamp checkedAt;
    private String username;

    public NotificationEntity to() {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setId(id);
        notificationEntity.setUserTestId(userTestId);
        notificationEntity.setUserTestUrl(userTestUrl);
        notificationEntity.setChangedDepId(changedDepId);
        notificationEntity.setUuid(uuid);
        notificationEntity.setChecked(checked);
        notificationEntity.setCreatedAt(createdAt);
        notificationEntity.setCheckedAt(checkedAt);
        notificationEntity.setUsername(username);
        return notificationEntity;
    }


    public static Notification from(NotificationEntity notificationEntity) {
        Notification dto = new Notification();
        dto.setId(notificationEntity.getId());
        dto.setUserTestId(notificationEntity.getUserTestId());
        dto.setUserTestUrl(notificationEntity.getUserTestUrl());
        dto.setChangedDepType(notificationEntity.getChangedDepType());
        dto.setChangedDepId(notificationEntity.getChangedDepId());
        dto.setUuid(notificationEntity.getUuid());
        dto.setChecked(notificationEntity.getChecked());
        dto.setCreatedAt(notificationEntity.getCreatedAt());
        dto.setCheckedAt(notificationEntity.getCheckedAt());
        dto.setUsername(notificationEntity.getUsername());
        return dto;
    }




}
