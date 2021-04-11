package it.nicolalopatriello.thesis.common.exception;

public class ColorRGBFormatException extends RuntimeException {
    public ColorRGBFormatException(String hex) {
        super(hex);
    }
}