package it.nicolalopatriello.thesis.common.exception;

import java.io.File;

public class FolderCreationException extends RuntimeException {
    public FolderCreationException(File f) {
        super(f != null ? f.getPath() : null);
    }
}
