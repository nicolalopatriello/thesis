package it.nicolalopatriello.thesis.worker.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class InvalidUrlException extends Exception {
    public InvalidUrlException(String reason) {
        super(reason);
    }
}