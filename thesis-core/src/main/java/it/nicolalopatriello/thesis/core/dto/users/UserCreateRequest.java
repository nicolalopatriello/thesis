package it.nicolalopatriello.thesis.core.dto.users;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

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
