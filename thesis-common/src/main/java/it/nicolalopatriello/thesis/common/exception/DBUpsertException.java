package it.nicolalopatriello.thesis.common.exception;

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
