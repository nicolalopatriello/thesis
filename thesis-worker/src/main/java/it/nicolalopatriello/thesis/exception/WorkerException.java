package it.nicolalopatriello.thesis.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class WorkerException extends Exception {
    public WorkerException(String reason) {
        super(reason);
    }
}