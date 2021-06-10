package it.nicolalopatriello.thesis.common.exception;


import it.nicolalopatriello.thesis.common.client.http.HttpClientResponse;

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
