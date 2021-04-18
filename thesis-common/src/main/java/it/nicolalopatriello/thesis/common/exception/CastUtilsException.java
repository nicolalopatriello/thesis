package it.nicolalopatriello.thesis.common.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class CastUtilsException extends Exception {
    public CastUtilsException(Exception e) {
        super(e);
    }
}
