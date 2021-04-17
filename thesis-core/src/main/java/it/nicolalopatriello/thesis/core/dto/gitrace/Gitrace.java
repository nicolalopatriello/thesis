package it.nicolalopatriello.thesis.core.dto.gitrace;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.GitraceEntity;
import it.nicolalopatriello.thesis.core.entities.TestVectorEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;

@Log4j
@Data
@ToString
public class Gitrace extends DTO {

    private Long id;
    private String gitRepoUrl;
    private String gitDescription;
    private GitProvider gitProvider;
    private String token;
    private Timestamp lastRepoUpdate;
    private Timestamp registrationTime;

    public GitraceEntity to() {
        GitraceEntity gitraceEntity = new GitraceEntity();
        gitraceEntity.setId(id);
        gitraceEntity.setGitRepoUrl(gitRepoUrl);
        gitraceEntity.setGitDescription(gitDescription);
        gitraceEntity.setGitProvider(gitProvider);
        gitraceEntity.setToken(token);
        gitraceEntity.setLastRepoUpdate(lastRepoUpdate);
        gitraceEntity.setRegistrationTime(registrationTime);
        return gitraceEntity;
    }


    public static Gitrace from(GitraceEntity gitraceEntity) {
        Gitrace dto = new Gitrace();
        dto.setId(gitraceEntity.getId());
        dto.setGitRepoUrl(gitraceEntity.getGitRepoUrl());
        dto.setGitDescription(gitraceEntity.getGitDescription());
        dto.setGitProvider(gitraceEntity.getGitProvider());
        dto.setToken(gitraceEntity.getToken());
        dto.setLastRepoUpdate(gitraceEntity.getLastRepoUpdate());
        dto.setRegistrationTime(gitraceEntity.getRegistrationTime());
        return dto;
    }




}
