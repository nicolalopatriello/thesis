package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/job")
public class JobController {

    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;

    @ThesisPublicApi
    @GetMapping(value = "/")
    @ResponseBody
    public WorkerJobResponse find() throws NotFoundException {
//        List<RepositoryEntity> repositoryEntityList = thesisRepositoryRepository.findAll();
//        if (repositoryEntityList.size() > 0)
//            return WorkerJobResponse.from(repositoryEntityList.get(0));
//        throw new NotFoundException();
        throw new NotImplementedException("Not impl");
    }
}