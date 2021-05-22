package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.core.dto.WithDTO;
import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;


@ToString
@Entity
@Table(name = "user_", schema = Schema.SCHEMA_NAME)
@Getter
@Setter
public class UserEntity implements WithDTO<User> {

    @Id
    @Column
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "registration_time")
    private Timestamp registrationTime;

    @Override
    public User dto() {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrationTime(new Timestamp(System.currentTimeMillis()));
        return user;
    }

    public static class Specification extends SimpleSearchSpecification<UserEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }

}
