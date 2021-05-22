package it.nicolalopatriello.thesis.worker.exception;

import it.nicolalopatriello.thesis.common.utils.WatcherType;

public class WatcherTypeNotFoundException extends RuntimeException {
    public WatcherTypeNotFoundException(WatcherType type) {
        super(type != null ? type.toString() : null);
    }
}
