package it.nicolalopatriello.thesis.worker.watchers;

import it.nicolalopatriello.thesis.common.utils.WatcherType;
import it.nicolalopatriello.thesis.worker.exception.WatcherTypeNotFoundException;
import it.nicolalopatriello.thesis.worker.watchers.impl.PythonWatcherImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WatcherFactory {

    public static Watcher<?> get(WatcherType type) {
        switch (type) {
            case PYTHON_DEPENDENCY:
                return new PythonWatcherImpl();
            default:
                throw new WatcherTypeNotFoundException(type);
        }

    }

}
