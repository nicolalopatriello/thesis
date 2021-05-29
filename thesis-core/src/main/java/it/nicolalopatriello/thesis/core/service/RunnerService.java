package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.core.Runner;

import java.util.Optional;

public interface RunnerService {
    Optional<Runner> allowedRunner(String secret);
}
