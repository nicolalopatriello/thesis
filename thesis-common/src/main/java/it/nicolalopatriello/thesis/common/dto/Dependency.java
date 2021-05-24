package it.nicolalopatriello.thesis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Dependency {
    private String name;
    private String version;
    private String programmingLanguage;
}