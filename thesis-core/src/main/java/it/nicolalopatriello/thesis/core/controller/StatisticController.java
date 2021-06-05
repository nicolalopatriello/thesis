package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.Statistic;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryDetails;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryLight;
import it.nicolalopatriello.thesis.core.service.RepositoryService;
import it.nicolalopatriello.thesis.core.service.StatisticService;
import it.nicolalopatriello.thesis.core.utils.JwtUser;
import it.nicolalopatriello.thesis.core.utils.ResponseEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @ThesisAuthorization
    @GetMapping(value = "/")
    @ResponseBody
    public Statistic create(JwtUser user) throws NotFoundException {
        return statisticService.find(user.getUsername());
    }

}