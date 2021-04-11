package it.nicolalopatriello.thesis.common.exception;

public class JwtExpiredTokenException extends Exception {
    public JwtExpiredTokenException(String s, Exception e) {
        super(s, e);
    }
}
