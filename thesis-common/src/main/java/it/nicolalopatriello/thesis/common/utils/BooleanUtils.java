package it.nicolalopatriello.thesis.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanUtils {
    public static final int FALSE = 0;
    public static final int TRUE = 1;

    public static boolean toBool(int i) {
        return i == TRUE;
    }

    public static int toInt(boolean b) {
        return b ? TRUE : FALSE;
    }

}
