package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryDetails;
import it.nicolalopatriello.thesis.core.service.RepositoryService;
import it.nicolalopatriello.thesis.core.utils.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;

    @ThesisAuthorization
    @PostMapping(value = "/")
    @ResponseBody
    public RepositoryCreateResponse create(JwtUser user, @Valid @RequestBody RepositoryCreateRequest request) throws BadRequestException {
        return repositoryService.create(user, request);
    }

    @ThesisAuthorization
    @GetMapping(value = "/{id}/")
    @ResponseBody
    public RepositoryDetails findById(@PathVariable Long id) throws NotFoundException {
        return repositoryService.findByIdWithDetails(id);
    }


}