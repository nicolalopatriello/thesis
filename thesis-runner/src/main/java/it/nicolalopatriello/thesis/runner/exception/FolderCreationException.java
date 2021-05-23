package it.nicolalopatriello.thesis.runner.exception;

import java.io.File;

public class FolderCreationException extends RuntimeException {
    public FolderCreationException(File f) {
        super(f != null ? f.getPath() : null);
    }
}
