package it.nicolalopatriello.thesis.core.dto.schedulerhistory;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SchedulerHistoryCreateRequest {
    @NotNull
    private SchedulerHistoryType type;
}
