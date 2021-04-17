package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.UserTestDepTestVector;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@EqualsAndHashCode(of = "url")
@Table(name = "user_test_dep_test_vector", schema = Schema.SCHEMA_NAME)
@Getter
@Setter
public class UserTestDepTestVectorEntity implements WithDTO<UserTestDepTestVector> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String url;

    @Column
    private Long testVectorId;

    @Override
    public UserTestDepTestVector dto() {
        UserTestDepTestVector userTestDepTestVector = new UserTestDepTestVector();
        userTestDepTestVector.setUrl(url);
        userTestDepTestVector.setTestVectorId(testVectorId);
        return userTestDepTestVector;
    }

    public static class Specification extends SimpleSearchSpecification<UserTestDepTestVectorEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }

}
