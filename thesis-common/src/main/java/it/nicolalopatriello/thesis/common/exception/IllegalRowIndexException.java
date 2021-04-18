package it.nicolalopatriello.thesis.common.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class IllegalRowIndexException extends Exception {
    public IllegalRowIndexException(String s) {
        super(s);
    }
}
