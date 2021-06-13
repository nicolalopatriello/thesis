package it.nicolalopatriello.thesis.runner.service;


import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;

public interface DockerInspectService {
    String inspect(String repositoryAddress, String image, String command, RunnerJobResponse.RepositoryCredentials creds);
}
