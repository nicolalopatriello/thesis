package it.nicolalopatriello.thesis.core.dto.usertest;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserTestCreateRequest {
    @NotNull
    private String gitRepoUrl;

    @NotNull
    private GitProvider gitProvider;

    @NotNull
    private List<Long> testVectorsDep = Lists.newLinkedList();

    @NotNull
    private List<Long> gitraceDep = Lists.newLinkedList();

    private String description;
}
