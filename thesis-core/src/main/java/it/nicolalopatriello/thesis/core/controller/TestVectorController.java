package it.nicolalopatriello.thesis.core.controller;

import it.nicolalopatriello.thesis.common.annotations.roles.ThesisAuthorization;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.service.TestVectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/test-vectors")
public class TestVectorController {

    @Autowired
    private TestVectorService testVectorService;

    @GetMapping(value = "/")
    @ResponseBody
    @ThesisAuthorization
    public List<TestVector> findAll() throws UnauthorizedException {
        return testVectorService.findAll();
    }
}