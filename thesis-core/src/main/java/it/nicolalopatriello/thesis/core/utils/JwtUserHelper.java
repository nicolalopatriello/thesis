package it.nicolalopatriello.thesis.core.utils;

import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.User;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUserHelper {
    public static JwtUser of(User user) {
        JwtUser u = new JwtUser();
        u.setUsername(user.getUsername());
        return u;
    }

    public static JwtUser of(UserEntity user) {
        JwtUser u = new JwtUser();
        u.setUsername(user.getUsername());
        return u;
    }
}
