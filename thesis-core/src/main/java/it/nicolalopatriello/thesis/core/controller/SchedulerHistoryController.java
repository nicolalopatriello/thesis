package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistory;
import it.nicolalopatriello.thesis.core.service.SchedulerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/scheduler-history")
public class SchedulerHistoryController {

    @Autowired
    private SchedulerHistoryService schedulerHistoryService;

    @GetMapping(value = "/")
    @ResponseBody
    @ThesisAuthorization
    public List<SchedulerHistory> findAll() throws UnauthorizedException {
        return schedulerHistoryService.findAll();
    }
}