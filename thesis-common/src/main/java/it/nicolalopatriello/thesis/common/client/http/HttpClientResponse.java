package it.nicolalopatriello.thesis.common.client.http;

import org.apache.http.HttpResponse;

public class HttpClientResponse {
    private final int code;
    private final String message;
    private final HttpResponse response;

    public HttpClientResponse(int code, String message, HttpResponse response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public boolean isOk() {
        return this.code == 200;
    }

    public boolean isAccepted() {
        return this.code == 202;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpResponse getResponse() {
        return this.response;
    }
}