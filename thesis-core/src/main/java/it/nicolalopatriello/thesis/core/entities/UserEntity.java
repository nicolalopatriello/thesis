package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
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
public class UserEntity {

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

    public static class Specification extends SimpleSearchSpecification<UserEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }

}
