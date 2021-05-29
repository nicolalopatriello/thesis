package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.DependencyLight;
import it.nicolalopatriello.thesis.common.exception.UnsopportedProgrammingLanguageException;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface VulnerabilitiesService {
    Optional<DependencyVulnerability> find(DependencyLight dependency) throws UnsopportedProgrammingLanguageException, FileNotFoundException;
}
