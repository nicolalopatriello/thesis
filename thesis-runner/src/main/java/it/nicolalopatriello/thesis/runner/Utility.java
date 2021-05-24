package it.nicolalopatriello.thesis.runner;


import it.nicolalopatriello.thesis.common.dto.Dependency;
import it.nicolalopatriello.thesis.common.dto.ProgrammingLanguage;
import lombok.extern.log4j.Log4j;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
public class Utility {
    private final static Pattern PYTHON_DEPENDENCY_PARSER_PATTERN = Pattern.compile("(?<NAME>.*)(?<TYPE>==|>=|<=)(?<VERSION>.*)");

    public static Optional<Dependency> parsePythonDependency(String dependency) {
        Matcher matcher = PYTHON_DEPENDENCY_PARSER_PATTERN.matcher(dependency.toLowerCase().replaceAll("\\s", ""));
        if (matcher.find()) {
            String name = matcher.group("NAME");
            String version = matcher.group("VERSION");
            return Optional.of(new Dependency(name, version, ProgrammingLanguage.PYTHON));
        }
        return Optional.empty();
    }

}

