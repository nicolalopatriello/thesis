package it.nicolalopatriello.thesis.core.dto.gitrace;

import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
public class GitraceCreateRequest {
    @NotNull
    private String gitRepoUrl;

    private String gitDescription;

    @NotNull
    private GitProvider gitProvider;
}
