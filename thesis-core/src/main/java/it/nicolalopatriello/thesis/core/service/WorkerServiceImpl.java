package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.entities.RunnerEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.repos.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    ThesisRepositoryRepository thesisRepositoryRepository;
    @Autowired
    RunnerRepository runnerRepository;

    @Override
    public RunnerJobResponse findJob() {
        return null;
    }

    @Override
    public Runner register(String secret) {
        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setSecret(secret);
        runnerEntity.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        return Runner.from(runnerRepository.save(runnerEntity));
    }
}
