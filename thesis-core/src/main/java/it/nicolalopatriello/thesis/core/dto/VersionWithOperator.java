package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.core.exception.UnsupportedSemVerOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Log4j
public class VersionWithOperator {

    private SemVerOperator operator;
    private String version;

    private final static Pattern VERSIONS_OPERATORS_PARSER_PATTERN = Pattern.compile("(?<OPERATOR>==|>=|<=|>|<)(?<VERSION>.*)");

    public static Optional<VersionWithOperator> from(String ver) {
        Matcher matcher = VERSIONS_OPERATORS_PARSER_PATTERN.matcher(ver.toLowerCase().replaceAll("\\s", ""));
        if (matcher.find()) {
            VersionWithOperator vo = new VersionWithOperator();
            String o = matcher.group("OPERATOR");
            String v = matcher.group("VERSION");
            if (o != null && v != null) {
                try {
                    vo.setOperator(getOperator(o));
                    vo.setVersion(v);
                    return Optional.of(vo);
                } catch (Exception e) {
                    log.error("Cannot extract Operator from input version " + ver);
                }
            }
        }
        return Optional.empty();
    }

    private static SemVerOperator getOperator(String o) throws UnsupportedSemVerOperator {
        switch (o) {
            case "==":
                return SemVerOperator.EQUALS;
            case ">=":
                return SemVerOperator.GREATER_THEN_OR_EQUAL_TO;
            case "<=":
                return SemVerOperator.LOWER_THEN_OR_EQUAL_TO;
            case ">":
                return SemVerOperator.GREATER;
            case "<":
                return SemVerOperator.LOWER;
            default:
                throw new UnsupportedSemVerOperator(o);
        }
    }

}

