package it.nicolalopatriello.thesis.core.dto;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User extends DTO {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String password;
    private Timestamp registrationTime;

    public static User of(UserEntity user) {
        User dto = new User();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPassword(user.getPassword());
        if (user.getRegistrationTime() != null) dto.setRegistrationTime(user.getRegistrationTime());
        return dto;
    }

    public UserEntity to() {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        if (user.getRegistrationTime() != null) user.setRegistrationTime(registrationTime);
        return user;
    }
}
