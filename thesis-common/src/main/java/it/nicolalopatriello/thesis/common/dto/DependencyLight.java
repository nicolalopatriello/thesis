package it.nicolalopatriello.thesis.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DependencyLight {
    private String name;
    private String version;
    private String programmingLanguage;

    public DependencyLight(String name, String version, String programmingLanguage) {
        this.name = name;
        this.version = version;
        this.programmingLanguage = programmingLanguage;
    }
}