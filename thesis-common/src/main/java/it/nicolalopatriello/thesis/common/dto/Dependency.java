package it.nicolalopatriello.thesis.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dependency {
    private String name;
    private String version;
    private String programmingLanguage;
}