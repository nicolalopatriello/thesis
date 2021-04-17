package it.nicolalopatriello.thesis.core.dto.testvector;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestVectorUpdateRequest {
    @NotNull
    private Long id;

    @NotNull
    private String hash;
}
