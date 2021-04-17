package it.nicolalopatriello.thesis.common.exception;

public class DuplicateEntityException extends Exception {
    public DuplicateEntityException(String name) {
        super(name != null ? name : "null");
    }
}
