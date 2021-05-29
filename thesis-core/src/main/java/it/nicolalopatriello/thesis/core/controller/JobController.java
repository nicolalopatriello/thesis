package it.nicolalopatriello.thesis.core.controller;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.entities.VulnerabilityEntity;
import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;
import it.nicolalopatriello.thesis.core.service.DependencyService;
import it.nicolalopatriello.thesis.core.service.RepositoryService;
import it.nicolalopatriello.thesis.core.service.RunnerServiceImpl;
import it.nicolalopatriello.thesis.core.service.VulnerabilityService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/job")
@Log4j
public class JobController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DependencyService dependencyService;


    @Autowired
    private RunnerServiceImpl runnerService;

    @Autowired
    private VulnerabilityService vulnerabilityService;

    @ThesisPublicApi
    @GetMapping(value = "/")
    @ResponseBody
    public RunnerJobResponse find(@RequestHeader("secret") String runnerSecret) throws NotFoundException, UnauthorizedException {
        Optional<Runner> runner = runnerService.allowedRunner(runnerSecret);
        if (runner.isPresent()) {
            List<RepositoryEntity> repositoryEntityList = repositoryService.findByRunnerIdIsNull();
            if (repositoryEntityList.size() > 0) {
                RepositoryEntity r = repositoryEntityList.get(0); //get first available job
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
                repositoryService.save(r);

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
    public void runnerResponse(@RequestBody Object runnerResp, @RequestHeader("secret") String runnerSecret) throws UnauthorizedException {
        Optional<Runner> runner = runnerService.allowedRunner(runnerSecret);
        if (runner.isPresent()) {
            RunnerResponse runnerResponse = Jsonizable.fromJson(runnerResp.toString(), RunnerResponse.class);
            Optional<RepositoryEntity> optRepository = repositoryService.findById(runnerResponse.getRepositoryId());
            if (optRepository.isPresent()) {

                //update repository with end runner job
                RepositoryEntity repositoryEntity = optRepository.get();
                repositoryEntity.setRunnerId(null);
                repositoryEntity.setRunnerStartedAt(null);
                repositoryEntity.setRunnerFinishedAt(new Timestamp(System.currentTimeMillis()));
                repositoryEntity.setLastCommitSha(runnerResponse.getCommitSha());
                repositoryService.save(repositoryEntity);

                for (WatcherResponse watcher : runnerResponse.getWatchers()) {
                    List<VulnerabilityEntity> vulnerabilities = Lists.newLinkedList();

                    //save dependencies of runner response
                    watcher.getDependencies().forEach(d -> {
                        DependencyEntity ent = new DependencyEntity();
                        ent.setRepositoryId(optRepository.get().getId());
                        ent.setName(d.getName());
                        ent.setProgrammingLanguage(d.getProgrammingLanguage());
                        ent.setVersion(d.getVersion());
                        try {
                            dependencyService.save(ent);
                        } catch (CveDetailsClientException e) {
                            log.error("Error during dependency save " + ent.getName());
                        }
                    });
                }
            }

        } else {
            throw new UnauthorizedException();
        }
    }
}