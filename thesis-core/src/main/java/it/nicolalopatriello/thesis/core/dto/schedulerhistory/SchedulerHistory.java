package it.nicolalopatriello.thesis.core.dto.schedulerhistory;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.DepType;
import it.nicolalopatriello.thesis.core.entities.SchedulerHistoryEntity;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;

@Log4j
@Data
public class SchedulerHistory extends DTO {

    private Long id;
    private DepType type;
    private Timestamp timestamp;

    public SchedulerHistoryEntity to() {
        SchedulerHistoryEntity schedulerHistory = new SchedulerHistoryEntity();
        schedulerHistory.setId(id);
        schedulerHistory.setType(type);
        schedulerHistory.setTimestamp(timestamp);
        return schedulerHistory;
    }


    public static SchedulerHistory from(SchedulerHistoryEntity schedulerHistory) {
        SchedulerHistory dto = new SchedulerHistory();
        dto.setId(schedulerHistory.getId());
        dto.setType(schedulerHistory.getType());
        dto.setTimestamp(schedulerHistory.getTimestamp());
        return dto;
    }


}
