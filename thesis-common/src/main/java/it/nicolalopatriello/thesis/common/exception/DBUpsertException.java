package it.nicolalopatriello.thesis.common.exception;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 2/13/20.
 */

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor
public class DBUpsertException extends Exception {
    public DBUpsertException(Exception e) {
        super(e);
    }

    public DBUpsertException(String s) {
        super(s);
    }
}
