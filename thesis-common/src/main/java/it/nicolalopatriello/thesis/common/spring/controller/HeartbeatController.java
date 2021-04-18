package it.nicolalopatriello.thesis.common.spring.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.nicolalopatriello.thesis.common.annotations.roles.ThesisPublicApi;
import it.nicolalopatriello.thesis.common.utils.ResponseEntityFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(value = "heartbeat")


@Log4j
@Controller
@RequestMapping(path = "/heartbeat")
public class HeartbeatController extends BaseController {

    @ThesisPublicApi
    @GetMapping(path = "/")
    @ResponseBody
    @ApiOperation(value = "heartbeat", notes = "")
    public ResponseEntity<Object> heartbeat() {
        return ResponseEntityFactory.ok();
    }

}