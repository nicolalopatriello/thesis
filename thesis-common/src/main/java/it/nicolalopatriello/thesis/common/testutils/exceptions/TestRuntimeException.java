package it.nicolalopatriello.thesis.common.testutils.exceptions;

import wiremock.com.fasterxml.jackson.core.JsonProcessingException;

public class TestRuntimeException extends RuntimeException {
    public TestRuntimeException(String s) {
    }

    public TestRuntimeException(JsonProcessingException e) {
        super(e);
    }
}

