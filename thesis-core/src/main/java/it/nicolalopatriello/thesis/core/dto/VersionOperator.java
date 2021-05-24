package it.nicolalopatriello.thesis.core.dto;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionOperator {
    private final static Pattern VERSIONS_OPERATORS_PARSER_PATTERN = Pattern.compile("(?<TYPE>==|>=|<=|>|<)(?<VERSION>.*)");

    private final static String LOWER = "<";
    private final static String LOWER_THEN_OR_EQUAL_TO = "<=";
    private final static String EQUALS = "=";
    private final static String GREATER_THEN_OR_EQUAL_TO = ">=";
    private final static String GREATER = ">";


    public static Optional<String> extract(String ver) {
        Matcher matcher = VERSIONS_OPERATORS_PARSER_PATTERN.matcher(ver.toLowerCase().replaceAll("\\s", ""));
        if (matcher.find()) {
            String v = matcher.group("VERSION");
            return v != null ? Optional.of(v) : Optional.empty();
        }
        return Optional.empty();
    }



}

