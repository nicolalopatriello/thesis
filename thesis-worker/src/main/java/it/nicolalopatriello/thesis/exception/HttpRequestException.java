package it.nicolalopatriello.thesis.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class HttpRequestException extends Exception {
    public HttpRequestException(String reason) {
        super(reason);
    }
}