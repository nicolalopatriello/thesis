package it.nicolalopatriello.thesis.common.spring.controller;


import it.nicolalopatriello.thesis.common.exception.NotFoundException;

import java.util.Optional;

public class BaseController {

    protected <E> E getOrNotFound(Optional<E> e) throws NotFoundException {
        return e.orElseThrow(NotFoundException::new);
    }

    protected void isNotFound(boolean b) throws NotFoundException {
        if (!b) throw new NotFoundException();
    }

}
