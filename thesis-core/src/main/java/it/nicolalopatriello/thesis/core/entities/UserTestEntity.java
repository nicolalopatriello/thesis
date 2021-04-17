package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistory;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@ToString
@Entity
@Table(name = "user_test", schema = Schema.SCHEMA_NAME)
@Getter
@Setter
public class UserTestEntity implements WithDTO<UserTest> {

    @Id
    @Column
    private String url;

    @Column
    private String description;

    @Column
    private String username;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Override
    public UserTest dto() {
        UserTest userTest = new UserTest();
        userTest.setUrl(url);
        userTest.setDescription(description);
        userTest.setUsername(username);
        userTest.setCreatedAt(createdAt);
        return userTest;
    }

    public static class Specification extends SimpleSearchSpecification<UserTestEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }

}
