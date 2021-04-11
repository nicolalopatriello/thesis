package it.nicolalopatriello.thesis.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexUtils {
    public static final String TABLE_FIELD = "[a-zA-Z]+(\\d*\\w)*";
    public static final String SELECT_TABLE_FIELD = "[a-zA-Z]+(\\d*\\w)*\\.[a-zA-Z]+(\\d*\\w)*";
}
