package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.service.PythonVulnerabilitiesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/job")
public class JobController {
    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;

    @Autowired
    private PythonVulnerabilitiesServiceImpl pythonVulnerabilitiesService;

    @ThesisPublicApi
    @GetMapping(value = "/")
    @ResponseBody
    public RunnerJobResponse find() throws NotFoundException {
        List<RepositoryEntity> repositoryEntityList = thesisRepositoryRepository.findByWorkerIdIsNull();
        if (repositoryEntityList.size() > 0) {
            RepositoryEntity r = repositoryEntityList.get(0); //get first available
            RunnerJobResponse runnerJobResponse = new RunnerJobResponse();
            runnerJobResponse.setRepositoryId(r.getId());
            runnerJobResponse.setRepositoryUrl(r.getUrl());
            runnerJobResponse.setRepositoryBranch(r.getBranch());
            runnerJobResponse.setLastCommitSha(r.getLastCommitSha());
            RunnerJobResponse.RepositoryCredentials c = new RunnerJobResponse.RepositoryCredentials();
            c.setRepositoryPassword(r.getPassword());
            c.setRepositoryUsername(r.getUsername());
            runnerJobResponse.setLastCommitSha(r.getLastCommitSha());
            runnerJobResponse.setCredentials(c);
            runnerJobResponse.setRecipe(Jsonizable.fromJson(r.getRecipe(), Recipe.class));
            return runnerJobResponse;
        }
        throw new NotFoundException();
    }

    @ThesisPublicApi
    @PostMapping(value = "/")
    public void runnerResponse(@RequestBody Object runnerResponse) {
        RunnerResponse r = Jsonizable.fromJson(runnerResponse.toString(), RunnerResponse.class);
        for (WatcherResponse watcher : r.getWatchers()) {
            List<DependencyVulnerability> t = pythonVulnerabilitiesService.find(watcher.getDependencies());
            for (DependencyVulnerability dependencyVulnerability : t) {
                System.err.println(dependencyVulnerability.toString());
            }
        }
    }
}