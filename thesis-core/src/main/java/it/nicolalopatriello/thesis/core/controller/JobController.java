package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.worker.WorkerJobResponse;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/job")
public class JobController {

    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;

    @ThesisPublicApi
    @GetMapping(value = "/")
    @ResponseBody
    public WorkerJobResponse find() throws NotFoundException {
        List<RepositoryEntity> repositoryEntityList = thesisRepositoryRepository.findAll();
        if (repositoryEntityList.size() > 0)
            return WorkerJobResponse.from(repositoryEntityList.get(0));
        throw new NotFoundException();
    }
}