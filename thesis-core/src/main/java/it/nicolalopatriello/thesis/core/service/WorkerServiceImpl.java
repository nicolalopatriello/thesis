package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import it.nicolalopatriello.thesis.core.Worker;
import it.nicolalopatriello.thesis.core.entities.WorkerEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.repos.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    ThesisRepositoryRepository thesisRepositoryRepository;
    @Autowired
    WorkerRepository workerRepository;

    @Override
    public WorkerJobResponse findJob() {
        return null;
    }

    @Override
    public Worker register(String secret) {
        WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setSecret(secret);
        workerEntity.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        return Worker.from(workerRepository.save(workerEntity));
    }
}
