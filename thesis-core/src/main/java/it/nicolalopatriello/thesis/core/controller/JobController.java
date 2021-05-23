package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.dto.Recipe;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/job")
public class JobController {
    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;

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
            RunnerJobResponse.RepositoryCredentials c = new RunnerJobResponse.RepositoryCredentials();
            c.setRepositoryPassword(r.getPassword());
            c.setRepositoryUsername(r.getUsername());
            runnerJobResponse.setCredentials(c);
            runnerJobResponse.setRecipe(Jsonizable.fromJson(r.getRecipe(), Recipe.class));
            return runnerJobResponse;
        }
        throw new NotFoundException();
    }
}