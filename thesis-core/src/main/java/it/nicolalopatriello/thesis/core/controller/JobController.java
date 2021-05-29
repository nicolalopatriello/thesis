package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.service.PythonVulnerabilitiesServiceImpl;
import it.nicolalopatriello.thesis.core.service.RunnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/job")
public class JobController {
    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;

    @Autowired
    private PythonVulnerabilitiesServiceImpl pythonVulnerabilitiesService;

    @Autowired
    private RunnerServiceImpl runnerService;

    @ThesisPublicApi
    @GetMapping(value = "/")
    @ResponseBody
    public RunnerJobResponse find(@RequestHeader("secret") String runnerSecret) throws NotFoundException, UnauthorizedException {
        Optional<Runner> runner = runnerService.allowedRunner(runnerSecret);
        if (runner.isPresent()) {
            List<RepositoryEntity> repositoryEntityList = thesisRepositoryRepository.findByRunnerIdIsNull();
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

                //update repository with current runner
                r.setRunnerId(runner.get().getId());
                r.setRunnerStartedAt(new Timestamp(System.currentTimeMillis()));
                thesisRepositoryRepository.save(r);

                return runnerJobResponse;
            }
            throw new NotFoundException();
        }
        throw new UnauthorizedException();
    }

    @ThesisPublicApi
    @PostMapping(value = "/")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void runnerResponse(@RequestBody Object runnerResponse, @RequestHeader("secret") String runnerSecret) throws UnauthorizedException {
        Optional<Runner> runner = runnerService.allowedRunner(runnerSecret);
        if (runner.isPresent()) {
            RunnerResponse r = Jsonizable.fromJson(runnerResponse.toString(), RunnerResponse.class);
            Optional<RepositoryEntity> optRepository = thesisRepositoryRepository.findById(r.getRepositoryId());

            if (optRepository.isPresent()) {
                //update repository with current runner
                RepositoryEntity repositoryEntity = optRepository.get();
                repositoryEntity.setRunnerId(null);
                repositoryEntity.setRunnerStartedAt(null);
                repositoryEntity.setRunnerFinishedAt(new Timestamp(System.currentTimeMillis()));
                thesisRepositoryRepository.save(repositoryEntity);

                for (WatcherResponse watcher : r.getWatchers()) {
                    List<DependencyVulnerability> t = pythonVulnerabilitiesService.find(watcher.getDependencies());
                    for (DependencyVulnerability dependencyVulnerability : t) {
                        System.err.println(dependencyVulnerability.toString());
                    }
                }
            }

        } else {
            throw new UnauthorizedException();
        }
    }
}