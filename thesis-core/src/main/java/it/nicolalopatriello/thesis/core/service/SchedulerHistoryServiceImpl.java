package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistory;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryCreateRequest;
import it.nicolalopatriello.thesis.core.entities.SchedulerHistoryEntity;
import it.nicolalopatriello.thesis.core.repos.SchedulerHistoryRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j
public class SchedulerHistoryServiceImpl implements SchedulerHistoryService {

    @Autowired
    private SchedulerHistoryRepository repository;


    @Override
    public SchedulerHistory create(SchedulerHistoryCreateRequest request) {
        SchedulerHistoryEntity entity = new SchedulerHistoryEntity();
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));
        entity.setType(request.getType());
        SchedulerHistory dto = repository.save(entity).dto();
        return dto;
    }


    @Override
    public List<SchedulerHistory> findAll() {
        return repository.findAll().stream().map(SchedulerHistoryEntity::dto).collect(Collectors.toList());
    }

}
