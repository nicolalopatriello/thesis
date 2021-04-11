package it.nicolalopatriello.thesis.common.exception;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 12/19/19.
 */

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@NoArgsConstructor
@Log4j
public class BadRequestException extends Exception {
    public BadRequestException(String s) {
        super(s);
    }
}
