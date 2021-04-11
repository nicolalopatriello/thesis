package it.nicolalopatriello.thesis.common.utils;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;


@Log4j
class TimeUtilsTest {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Long now = TimeUtils.now();

    @Test
    void test_now() {
        long before = TimeUtils.now();
        long time = TimeUtils.nowTimestamp().getTime();
        long after = TimeUtils.now();

        assertTrue(time >= before);
        assertTrue(after >= time);
    }

    @Test
    void minus_n_days() {
        long now = TimeUtils.now();
        assertEquals(now - 2 * TimeUtils.MILLISEC_IN_A_DAY, TimeUtils.minusNdays(now, 2));
    }

    @Test
    void minus_365_days() {
        long now = TimeUtils.now();
        assertEquals(now - TimeUtils.MILLISEC_IN_A_DAY * 365, TimeUtils.minus365days(now));
    }

    @Test
    void add_millisec() {
        assertEquals(TimeUtils.now() + 50, TimeUtils.addMillisec(50L).getTime(), 10);
    }

    @Test
    void kirk_timezone() {
        TimeZone timeZone = TimeUtils.kirkTimezone();
        assertEquals(TimeZone.getTimeZone("UTC"), timeZone);
    }

    @Test
    void calculate_millisec_in_minute() {
        long millisec = TimeUtils.calculateMillisecInMinute(20);
        assertEquals(20 * TimeUtils.MILLISEC_IN_A_MINUTE, millisec);
    }

    @Test
    void get_time_by_string() {
        long millisec = TimeUtils.getTimeByString("10s");
        assertEquals(10 * TimeUtils.MILLISECS_IN_A_SECOND, millisec);

        millisec = TimeUtils.getTimeByString("20m");
        assertEquals(20 * TimeUtils.MILLISEC_IN_A_MINUTE, millisec);

        millisec = TimeUtils.getTimeByString("30h");
        assertEquals(30 * TimeUtils.MILLISEC_IN_A_HOUR, millisec);

        millisec = TimeUtils.getTimeByString("40d");
        assertEquals(40 * TimeUtils.MILLISEC_IN_A_DAY, millisec);

        assertThrows(IllegalArgumentException.class, () -> TimeUtils.getTimeByString("40k"));

        assertThrows(IllegalArgumentException.class, () -> TimeUtils.getTimeByString("z40"));
    }

    @Test
    void date_format_with_kirk_timezone() {
        DateFormat dateFormat = TimeUtils.dateFormatWithKirkTimezone("yyyyMMdd HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        sdf.setTimeZone(TimeUtils.kirkTimezone());
        assertEquals(sdf, dateFormat);
    }
}