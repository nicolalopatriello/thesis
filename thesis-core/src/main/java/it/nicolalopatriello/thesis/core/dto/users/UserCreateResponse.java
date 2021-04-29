package it.nicolalopatriello.thesis.core.dto.users;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateResponse {
    @NotNull
    private String username;

    @NotNull
    private String email;
}
