package it.nicolalopatriello.thesis.core.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class DirectoryCreationException extends Exception {
    public DirectoryCreationException(String destinationDir) {
        super(String.format("dir=%s", destinationDir));
    }
}
