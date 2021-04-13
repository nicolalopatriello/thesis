package it.nicolalopatriello.thesis.core.dto.testvector;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
public class TestVectorCreateRequest {
    @NotNull
    private String fileName;

    @NotNull
    private String hash;

    @NotNull
    private String path;

    @NotNull
    private String url;
}
