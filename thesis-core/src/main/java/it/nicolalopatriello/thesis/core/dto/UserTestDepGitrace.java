package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.UserTestDepGitraceEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
@ToString
public class UserTestDepGitrace extends DTO {

    private String url;
    private Long gitraceId;

    public UserTestDepGitraceEntity to() {
        UserTestDepGitraceEntity userTestDepGitraceEntity = new UserTestDepGitraceEntity();
        userTestDepGitraceEntity.setUrl(url);
        userTestDepGitraceEntity.setGitraceId(gitraceId);
        return userTestDepGitraceEntity;
    }


    public static UserTestDepGitrace from(UserTestDepGitraceEntity userTestDepGitraceEntity) {
        UserTestDepGitrace dto = new UserTestDepGitrace();
        dto.setUrl(userTestDepGitraceEntity.getUrl());
        dto.setGitraceId(userTestDepGitraceEntity.getGitraceId());
        return dto;
    }


}
