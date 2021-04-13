package it.nicolalopatriello.thesis.core.dto.users;

import it.nicolalopatriello.thesis.core.dto.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class LoginResponse {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String token;

    public static LoginResponse of(User userDTO, String tok) {
        LoginResponse dto = new LoginResponse();
        dto.setUsername(userDTO.getUsername());
        dto.setToken(tok);
        return dto;
    }

}
