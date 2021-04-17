package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistory;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;

import java.util.List;


public interface SchedulerHistoryService {
    List<SchedulerHistory> findAll();

    SchedulerHistory create(SchedulerHistoryCreateRequest dto);
}
