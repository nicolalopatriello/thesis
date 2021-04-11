package it.nicolalopatriello.thesis.common.utils;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.util.function.Supplier;

@AllArgsConstructor
public class LoggerUtils {

    private Logger log;

    public void debug(Supplier<String> message) {
        if (log.isDebugEnabled()) log.debug(message.get());
    }
}
