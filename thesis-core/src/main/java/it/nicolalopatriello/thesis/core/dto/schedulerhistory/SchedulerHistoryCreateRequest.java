package it.nicolalopatriello.thesis.core.dto.schedulerhistory;

import it.nicolalopatriello.thesis.core.dto.DepType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SchedulerHistoryCreateRequest {
    @NotNull
    private DepType type;
}
