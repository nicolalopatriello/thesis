package it.nicolalopatriello.thesis.core.dto.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreateRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private String name;

}
