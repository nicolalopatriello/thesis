package it.nicolalopatriello.thesis.common.utils;

/**
 * Created by Greta Sasso <greta.sasso@gfmintegration.it> on 2/19/20.
 */

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomStringUtils;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtils {
    private static final int UUID_LENGTH = 18;

    public static String generate() {
        String str = RandomStringUtils.random(5, true, false) + System.currentTimeMillis();
        return str.substring(0, UUID_LENGTH);
    }
}

