package it.nicolalopatriello.thesis.common.exception;

public class UnsopportedProgrammingLanguageException extends Exception {
    public UnsopportedProgrammingLanguageException(String programmingLanguage) {
        super(programmingLanguage);
    }
}
