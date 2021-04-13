package it.nicolalopatriello.thesis.core.dto;


import it.nicolalopatriello.thesis.core.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User {
    private String username;
    private String email;
    private String name;
    private String surname;
    private Long registrationTime;

    public static User of(UserEntity user) {
        User dto = new User();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        if (user.getRegistrationTime() != null) dto.setRegistrationTime(user.getRegistrationTime().getTime());
        return dto;
    }

    public UserEntity to() {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        if (user.getRegistrationTime() != null) user.setRegistrationTime(new Timestamp(registrationTime));
        return user;
    }
}
