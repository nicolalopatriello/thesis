package it.nicolalopatriello.thesis.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityFactory {
    public static ResponseEntity<Object> ok() {
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<Object> internalServerError() {
        return of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Object> badRequest() {
        return ResponseEntity.badRequest().build();
    }

    public static ResponseEntity<Object> of(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }

    public static ResponseEntity<Object> of(int code) {
        return ResponseEntity.status(code).build();
    }

    public static ResponseEntity<Object> noContent() {
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<Object> notFound() {
        return ResponseEntity.notFound().build();
    }

    public static ResponseEntity<Object> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}