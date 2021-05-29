package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.DependencyLight;
import it.nicolalopatriello.thesis.common.exception.UnsopportedProgrammingLanguageException;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;

import java.io.FileNotFoundException;
import java.util.Optional;

public class DockerVulnerabilitiesServiceImpl implements DockerVulnerabilitiesService {
    @Override
    public Optional<DependencyVulnerability> find(DependencyLight dependency) throws UnsopportedProgrammingLanguageException, FileNotFoundException {
        return Optional.empty();
    }
}
