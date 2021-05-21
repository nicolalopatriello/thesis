package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.core.dto.worker.WorkerJobResponse;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    ThesisRepositoryRepository thesisRepositoryRepository;


    @Override
    public WorkerJobResponse findJob() {
        return null;
    }
}
