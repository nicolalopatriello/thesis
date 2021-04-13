package it.nicolalopatriello.thesis.core.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class DownloadFileException extends Exception {
    public DownloadFileException(String completeUrl, String pdfFile) {
        super(String.format("url=%s, pdfFile=%s", completeUrl, pdfFile));
    }
}
