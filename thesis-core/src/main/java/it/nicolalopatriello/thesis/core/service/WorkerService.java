package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.core.Runner;

public interface WorkerService {
    RunnerJobResponse findJob();
    Runner register(String secret);
}
