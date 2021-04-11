package it.nicolalopatriello.thesis.common.utils;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("UUID Utils")
@Log4j
class UUIDUtilsTest {

    public static final String MATCH_NUMBER = "\\d";

    @Test
    void generate() {
        String generate = UUIDUtils.generate();
        String first = String.valueOf(generate.charAt(0));
        String second = String.valueOf(generate.charAt(1));
        String third = generate.substring(2);
        assertFalse(first.matches(MATCH_NUMBER));
        assertFalse(second.matches(MATCH_NUMBER));
        assertTrue(third.matches(".*\\d+.*"));
    }
}