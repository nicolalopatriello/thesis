package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.entities.RunnerEntity;
import it.nicolalopatriello.thesis.core.repos.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RunnerServiceImpl implements RunnerService {
    @Autowired
    RunnerRepository runnerRepository;

    @Override
    public Optional<Runner> allowedRunner(String secret) {
        Optional<RunnerEntity> r = runnerRepository.findBySecret(secret);
        return r.map(Runner::from);
    }
}
