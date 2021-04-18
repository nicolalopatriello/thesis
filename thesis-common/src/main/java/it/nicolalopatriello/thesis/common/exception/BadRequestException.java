package it.nicolalopatriello.thesis.common.exception;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@NoArgsConstructor
@Log4j
public class BadRequestException extends Exception {
    public BadRequestException(String s) {
        super(s);
    }
}
