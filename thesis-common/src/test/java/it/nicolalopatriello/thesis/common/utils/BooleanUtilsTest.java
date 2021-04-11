package it.nicolalopatriello.thesis.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanUtilsTest {

    @Test
    public void to_bool() {
        assertTrue(BooleanUtils.toBool(BooleanUtils.TRUE));
        assertFalse(BooleanUtils.toBool(BooleanUtils.FALSE));
    }

    @Test
    public void aVoid() {
        assertEquals(1, BooleanUtils.toInt(true));
        assertEquals(0, BooleanUtils.toInt(false));
    }

}