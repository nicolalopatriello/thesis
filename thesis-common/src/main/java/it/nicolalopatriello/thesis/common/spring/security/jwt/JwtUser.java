package it.nicolalopatriello.thesis.common.spring.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtUser {
    private String username;
    private String role;
    private boolean enabled;
    private boolean changePasswordRequired;
}