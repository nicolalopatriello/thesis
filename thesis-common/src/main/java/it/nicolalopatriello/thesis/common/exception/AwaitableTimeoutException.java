package it.nicolalopatriello.thesis.common.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class AwaitableTimeoutException extends Exception {
    public AwaitableTimeoutException(int repetition, long interval) {
        super(String.format("repetition=%s, interval=%s", repetition, interval));
    }
}
