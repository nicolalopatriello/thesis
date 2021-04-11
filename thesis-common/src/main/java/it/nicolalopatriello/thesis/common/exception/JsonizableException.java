package it.nicolalopatriello.thesis.common.exception;

public class JsonizableException extends Exception {
    public JsonizableException(Exception e) {
        super(e);
    }
}
