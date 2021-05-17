package it.nicolalopatriello.thesis.core.dto.dependecy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PythonDependency {
    String name;
    String version;
}
