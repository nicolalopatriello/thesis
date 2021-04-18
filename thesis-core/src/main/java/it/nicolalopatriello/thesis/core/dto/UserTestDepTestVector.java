package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestDepTestVectorEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;

@Log4j
@Data
@ToString
public class UserTestDepTestVector extends DTO {

    private Long id;
    private Long userTestId;
    private String url;
    private Long testVectorId;

    public UserTestDepTestVectorEntity to() {
        UserTestDepTestVectorEntity userTestDepTestVectorEntity = new UserTestDepTestVectorEntity();
        userTestDepTestVectorEntity.setId(id);
        userTestDepTestVectorEntity.setUserTestId(userTestId);
        userTestDepTestVectorEntity.setUrl(url);
        userTestDepTestVectorEntity.setTestVectorId(testVectorId);
        return userTestDepTestVectorEntity;
    }


    public static UserTestDepTestVector from(UserTestDepTestVectorEntity userTestDepTestVectorEntity) {
        UserTestDepTestVector dto = new UserTestDepTestVector();
        dto.setId(userTestDepTestVectorEntity.getId());
        dto.setUserTestId(userTestDepTestVectorEntity.getUserTestId());
        dto.setUrl(userTestDepTestVectorEntity.getUrl());
        dto.setTestVectorId(userTestDepTestVectorEntity.getTestVectorId());
        return dto;
    }




}
