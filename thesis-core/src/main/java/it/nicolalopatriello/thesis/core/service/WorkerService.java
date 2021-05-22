package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import it.nicolalopatriello.thesis.core.Worker;

public interface WorkerService {
    WorkerJobResponse findJob();
    Worker register(String secret);
}
