package it.nicolalopatriello.thesis.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Response Entity Factory")
class ResponseEntityFactoryTest {

    @Test
    void ok() {
        assertEquals(HttpStatus.OK, ResponseEntityFactory.ok().getStatusCode());
    }

    @Test
    void internalServerError() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ResponseEntityFactory.internalServerError().getStatusCode());
    }

    @Test
    void badRequest() {
        assertEquals(HttpStatus.BAD_REQUEST, ResponseEntityFactory.badRequest().getStatusCode());
    }

    @Test
    void of_code() {
        assertEquals(HttpStatus.OK, ResponseEntityFactory.of(HttpStatus.OK).getStatusCode());
    }

    @Test
    void of_int() {
        assertEquals(HttpStatus.OK, ResponseEntityFactory.of(200).getStatusCode());
    }

    @Test
    void noContent() {
        assertEquals(HttpStatus.NO_CONTENT, ResponseEntityFactory.noContent().getStatusCode());
    }

    @Test
    void notFound() {
        assertEquals(HttpStatus.NOT_FOUND, ResponseEntityFactory.notFound().getStatusCode());
    }

    @Test
    void forbidden() {
        assertEquals(HttpStatus.FORBIDDEN, ResponseEntityFactory.forbidden().getStatusCode());
    }
}