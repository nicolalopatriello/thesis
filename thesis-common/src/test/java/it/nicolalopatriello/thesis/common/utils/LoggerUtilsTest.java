package it.nicolalopatriello.thesis.common.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerUtilsTest {

    @Test
    void print_log_with_debug_enable() {
        Logger log = Logger.getLogger(LoggerUtilsTest.class);
        log.setLevel(Level.DEBUG);
        LoggerUtils loggerUtils = new LoggerUtils(log);
        loggerUtils.debug(() -> "test");
        assertTrue(true);

        LoggerUtils loggerUtilsNull = new LoggerUtils(null);
        assertThrows(NullPointerException.class, () -> loggerUtilsNull.debug(() -> "test"));
    }

    @Test
    void print_log_with_debug_disable() {
        Logger log = Logger.getLogger(LoggerUtilsTest.class);
        log.setLevel(Level.INFO);
        LoggerUtils loggerUtils = new LoggerUtils(log);
        loggerUtils.debug(() -> "test");
        assertTrue(true);
    }

    @Test
    void debug_throwable_exception() {
        Logger log = Logger.getLogger(LoggerUtilsTest.class);
        log.setLevel(Level.INFO);
        LoggerUtils loggerUtils = new LoggerUtils(log);
        loggerUtils.debug(() -> "test");
        assertTrue(true);
    }

}