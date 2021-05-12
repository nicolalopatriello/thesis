package it.nicolalopatriello.thesis.core.dto.connection;

import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ConnectionCreateRequest {
    @NotNull
    private GitProvider gitProvider;

    @NotNull
    private String endpoint;

    @NotNull
    private String token;
}
