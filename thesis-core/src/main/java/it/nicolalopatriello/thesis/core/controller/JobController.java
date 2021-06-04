package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.dto.WatcherResponse;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.utils.TimeUtils;
import it.nicolalopatriello.thesis.core.Runner;
import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.entities.MetricEntity;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;
import it.nicolalopatriello.thesis.core.service.DependencyService;
import it.nicolalopatriello.thesis.core.service.MetricService;
import it.nicolalopatriello.thesis.core.service.RepositoryService;
import it.nicolalopatriello.thesis.core.service.RunnerServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.nicolalopatriello.thesis.common.utils.ThesisConstant.RUNNER_SECRET_KEY;


@RestController
@RequestMapping(path = "/job")
@Log4j
public class JobController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DependencyService dependencyService;

    @Autowired
    private MetricService metricService;

    @Autowired
    private RunnerServiceImpl runnerService;

    @ThesisPublicApi
    @GetMapping(value = "/")
    @ResponseBody
    public RunnerJobResponse find(@RequestHeader(RUNNER_SECRET_KEY) String runnerSecret) throws NotFoundException, UnauthorizedException {
        Optional<Runner> runner = runnerService.allowedRunner(runnerSecret);
        if (runner.isPresent()) {
            List<RepositoryEntity> repositoryEntityList = repositoryService.findByRunnerIdIsNull();

            //find all available jobs
            List<RepositoryEntity> available = repositoryEntityList.stream().filter(repo ->
                    repo.getRunnerFinishedAt() == null ||
                            (new Date().getTime() -
                                    repo.getRunnerFinishedAt().getTime()) >
                                    repo.getMinutesWatchersInterval() * 60 * 1000)
                    .collect(Collectors.toList());

            if (available.size() > 0) {
                RepositoryEntity r = repositoryEntityList.get(0);
                RunnerJobResponse runnerJobResponse = new RunnerJobResponse();
                runnerJobResponse.setRepositoryId(r.getId());
                runnerJobResponse.setRepositoryUrl(r.getUrl());
                runnerJobResponse.setRepositoryBranch(r.getBranch());
                RunnerJobResponse.RepositoryCredentials c = new RunnerJobResponse.RepositoryCredentials();
                c.setRepositoryPassword(r.getPassword());
                c.setRepositoryUsername(r.getUsername());
                runnerJobResponse.setLastCommitSha(r.getLastCommitSha());
                runnerJobResponse.setCredentials(c);
                runnerJobResponse.setRecipe(Jsonizable.fromJson(r.getRecipe(), Recipe.class));

                //update repository with current runner
                r.setRunnerId(runner.get().getId());
                r.setRunnerStartedAt(new Timestamp(System.currentTimeMillis()));
                r.setRunnerFinishedAt(null);
                repositoryService.save(r);

                return runnerJobResponse;
            }
            log.info("No available jobs found");
            throw new NotFoundException();
        }
        throw new UnauthorizedException();
    }

    @ThesisPublicApi
    @PostMapping(value = "/")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void runnerResponse(@RequestBody RunnerResponse runnerResponse, @RequestHeader(RUNNER_SECRET_KEY) String runnerSecret) throws UnauthorizedException {
        Optional<Runner> runner = runnerService.allowedRunner(runnerSecret);
        if (runner.isPresent()) {
            Optional<RepositoryEntity> optRepository = repositoryService.findById(runnerResponse.getRepositoryId());
            if (optRepository.isPresent()) {
                RepositoryEntity repositoryEntity = optRepository.get();

                /*
                 * Remove all already found vulns, deps and metrics.
                 * Only if code has changed
                 * */
                if (repositoryEntity.getLastCommitSha() != null && (!repositoryEntity.getLastCommitSha().equals(runnerResponse.getCommitSha())))
                    repositoryService.clearReferences(optRepository.get().getId());

                repositoryEntity.setRunnerId(null);
                repositoryEntity.setRunnerStartedAt(null);
                repositoryEntity.setRunnerFinishedAt(new Timestamp(System.currentTimeMillis()));
                repositoryEntity.setLastCommitSha(runnerResponse.getCommitSha());
                repositoryService.save(repositoryEntity);

                for (WatcherResponse watcher : runnerResponse.getWatchers()) {
                    //save dependencies of runner response

                    if (watcher.getDependencies() != null) {
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

                    //save metrics of runner response
                    if (watcher.getMetrics() != null) {
                        watcher.getMetrics().forEach(m -> {
                            MetricEntity metric = new MetricEntity();
                            metric.setRepositoryId(repositoryEntity.getId());
                            metric.setDescription(m.getDescription());
                            metric.setWatcherSource(m.getWatcherType());
                            metric.setSeverity(m.getSeverity());
                            metric.setTimestamp(TimeUtils.nowTimestamp());
                            metricService.save(metric);
                        });
                    }

                }


            }
        } else {
            throw new UnauthorizedException();
        }
    }
}