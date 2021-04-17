package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.UserTestDepGitrace;
import it.nicolalopatriello.thesis.core.dto.UserTestDepTestVector;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@ToString
@Entity
@Table(name = "user_test_dep_gitrace", schema = Schema.SCHEMA_NAME)
@Getter
@Setter
public class UserTestDepGitraceEntity implements WithDTO<UserTestDepGitrace> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String url;

    @Column
    private Long gitraceId;

    @Override
    public UserTestDepGitrace dto() {
        UserTestDepGitrace userTestDepGitrace = new UserTestDepGitrace();
        userTestDepGitrace.setUrl(url);
        userTestDepGitrace.setGitraceId(gitraceId);
        return userTestDepGitrace;
    }

    public static class Specification extends SimpleSearchSpecification<UserTestDepGitraceEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }

}
