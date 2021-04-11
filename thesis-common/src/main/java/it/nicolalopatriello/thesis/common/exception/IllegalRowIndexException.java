package it.nicolalopatriello.thesis.common.exception;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 1/27/20.
 */

import lombok.extern.log4j.Log4j;

@Log4j
public class IllegalRowIndexException extends Exception {
    public IllegalRowIndexException(String s) {
        super(s);
    }
}
