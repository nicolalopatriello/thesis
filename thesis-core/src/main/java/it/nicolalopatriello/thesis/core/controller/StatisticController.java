package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.Statistic;
import it.nicolalopatriello.thesis.core.service.StatisticService;
import it.nicolalopatriello.thesis.core.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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