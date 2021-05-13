package it.nicolalopatriello.thesis.core.dto.gitrace;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GitraceCreateRequest {
    @NotNull
    private String gitRepoUrl;

    @NotNull
    private GitProvider gitProvider;

    private Long connectionId;

    private String gitDescription;

}
