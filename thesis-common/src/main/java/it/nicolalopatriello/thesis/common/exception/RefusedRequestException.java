package it.nicolalopatriello.thesis.common.exception;

import com.gfmi.net.HttpClientResponse;

public class RefusedRequestException extends Exception {
    public RefusedRequestException(HttpClientResponse res) {
        super(String.format("Code: %d Message: %s", res.getCode(), res.getMessage()));
    }

    public RefusedRequestException(Exception e) {
        super(e);
    }

    public RefusedRequestException(String msg) {
        super(msg);
    }
}
