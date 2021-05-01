package it.nicolalopatriello.thesis.core.dto.usertest;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserTestCreateResponse {
    @NotNull
    private String gitRepoUrl;
    private String description;
}
