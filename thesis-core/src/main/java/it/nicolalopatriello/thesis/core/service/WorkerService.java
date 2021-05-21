package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.core.dto.worker.WorkerJobResponse;

public interface WorkerService {
    WorkerJobResponse findJob();
}
