package it.nicolalopatriello.thesis.common.exception;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 4/6/20.
 */

import lombok.extern.log4j.Log4j;

@Log4j
public class CastUtilsException extends Exception {
    public CastUtilsException(Exception e) {
        super(e);
    }
}
