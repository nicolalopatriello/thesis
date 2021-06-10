package it.nicolalopatriello.thesis.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtils {

    public static final long HOURS_IN_A_DAY = 24;
    public static final long SECONDS_IN_A_MINUTE = 60;
    public static final long MINUTES_IN_A_HOUR = 60;
    public static final long MILLISECS_IN_A_SECOND = 1000;
    public static final long MILLISEC_IN_A_MINUTE = SECONDS_IN_A_MINUTE * MILLISECS_IN_A_SECOND;
    public static final long MILLISEC_IN_A_HOUR = MINUTES_IN_A_HOUR * MILLISEC_IN_A_MINUTE;
    public static final long MILLISEC_IN_A_DAY = HOURS_IN_A_DAY * MILLISEC_IN_A_HOUR;

    public static void awaitFor(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static long now() {
        return System.currentTimeMillis();
    }


    public static Timestamp nowTimestamp() {
        return new Timestamp(now());
    }
}
