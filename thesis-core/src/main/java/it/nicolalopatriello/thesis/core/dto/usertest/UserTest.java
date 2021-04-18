package it.nicolalopatriello.thesis.core.dto.usertest;


import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class UserTest extends DTO {

    private Long id;
    private String url;
    private String description;
    private String username;
    private Timestamp createdAt;

    public UserTestEntity to() {
        UserTestEntity userTestEntity = new UserTestEntity();
        userTestEntity.setId(id);
        userTestEntity.setUrl(url);
        userTestEntity.setDescription(description);
        userTestEntity.setUsername(username);
        userTestEntity.setCreatedAt(createdAt);
        return userTestEntity;
    }


    public static UserTest from(UserTestEntity userTestEntity) {
        UserTest dto = new UserTest();
        dto.setId(userTestEntity.getId());
        dto.setUrl(userTestEntity.getUrl());
        dto.setDescription(userTestEntity.getDescription());
        dto.setUsername(userTestEntity.getUsername());
        dto.setCreatedAt(userTestEntity.getCreatedAt());
        return dto;
    }




}
