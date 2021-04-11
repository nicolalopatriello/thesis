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


    public static TimeZone kirkTimezone() {
        String tz = Optional.ofNullable(System.getenv("KIRK_TIMEZONE")).orElse("UTC");  //NOSONAR
        return TimeZone.getTimeZone(tz);
    }


    public static long now() {
        return System.currentTimeMillis();
    }


    public static Timestamp nowTimestamp() {
        return new Timestamp(now());
    }

    public static long getTimeByString(String s) {
        Pattern pattern = Pattern.compile("^(?<value>\\d+\\d*)+(?<unit>[dhms])");   //NOSONAR
        Matcher res = pattern.matcher(s.toLowerCase());
        if (res.find()) {
            long value = Long.parseLong(res.group("value"));
            String unit = res.group("unit");
            switch (unit) {
                case "d":
                    return value * MILLISEC_IN_A_DAY;
                case "h":
                    return value * MILLISEC_IN_A_HOUR;
                case "m":
                    return value * MILLISEC_IN_A_MINUTE;
                case "s":
                    return value * MILLISECS_IN_A_SECOND;
                default:
                    throw new IllegalArgumentException("Unsupported unit in " + s + " [unit=" + unit + ", pattern=" + pattern.pattern() + "]");
            }
        } else
            throw new IllegalArgumentException("Expected time pattern=" + pattern.pattern());
    }

    public static Calendar getNormalizedCalendarDailyTime(Timestamp t) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(kirkTimezone());
        calendar.setTimeInMillis(t.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static long getNormalizedDailyTime(Timestamp t) {
        return getNormalizedCalendarDailyTime(t).getTimeInMillis();
    }

    public static Long calculateMillisecInDays(long days) {
        return MILLISEC_IN_A_DAY * days;
    }

    public static Long calculateMillisecInMinute(long minutes) {
        return MILLISEC_IN_A_MINUTE * minutes;
    }

    public static Long minusNdays(long time, long numberOfDays) {
        return time - calculateMillisecInDays(numberOfDays);
    }

    public static Long minus365days(long time) {
        return time - calculateMillisecInDays(365);
    }

    public static Timestamp addMillisec(Long millisec) {
        return new Timestamp(TimeUtils.now() + millisec);
    }

    public static DateFormat dateFormatWithKirkTimezone(String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(kirkTimezone());
        return dateFormat;
    }
}
