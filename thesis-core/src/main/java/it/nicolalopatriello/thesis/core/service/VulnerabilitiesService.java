package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.Dependency;
import it.nicolalopatriello.thesis.common.exception.UnsopportedProgrammingLanguageException;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;

import java.io.FileNotFoundException;
import java.util.List;

public interface VulnerabilitiesService {
    List<DependencyVulnerability> find(List<Dependency> dependencies) throws UnsopportedProgrammingLanguageException, FileNotFoundException;
}