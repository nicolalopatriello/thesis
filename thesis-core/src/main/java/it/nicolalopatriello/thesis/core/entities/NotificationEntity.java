package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.core.dto.WithDTO;
import it.nicolalopatriello.thesis.core.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.core.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.DepType;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "notification", schema = Schema.SCHEMA_NAME)
@Data
public class NotificationEntity implements WithDTO<Notification> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column(name = "user_test_id")
    private Long userTestId;

    @NotNull
    @Column(name = "user_test_url")
    private String userTestUrl;

    @NotNull
    @Column(name = "changed_dep_type")
    private DepType changedDepType;

    @NotNull
    @Column(name = "changed_dep_id")
    private Long changedDepId;

    @NotNull
    @Column
    private String uuid;

    @Column
    private Integer checked;

    @NotNull
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "checked_at")
    private Timestamp checkedAt;

    @Column
    private String username;


    @Override
    public Notification dto() {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setUserTestId(userTestId);
        notification.setUserTestUrl(userTestUrl);
        notification.setChangedDepType(changedDepType);
        notification.setChangedDepId(changedDepId);
        notification.setUuid(uuid);
        notification.setChecked(checked);
        notification.setCreatedAt(createdAt);
        notification.setCheckedAt(checkedAt);
        notification.setUsername(username);
        return notification;
    }

    public static class Specification extends SimpleSearchSpecification<NotificationEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}