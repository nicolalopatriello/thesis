package it.nicolalopatriello.thesis.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception {
    public NotFoundException(String s) {
        super(s != null ? s : "null");
    }

    public NotFoundException(Class<?> clazz, Object value) {
        super(String.format("Class=%s, value=%s", clazz.getSimpleName(), value));
    }
}
