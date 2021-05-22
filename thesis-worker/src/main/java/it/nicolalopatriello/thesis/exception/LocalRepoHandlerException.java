package it.nicolalopatriello.thesis.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class LocalRepoHandlerException extends Exception {
    public LocalRepoHandlerException(String reason) {
        super(reason);
    }
}