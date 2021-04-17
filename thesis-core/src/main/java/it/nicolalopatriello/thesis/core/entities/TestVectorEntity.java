package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "test_vector", schema = Schema.SCHEMA_NAME)
@Data
public class TestVectorEntity implements WithDTO<TestVector> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotBlank
    @NotNull
    @Column
    private String url;

    @NotBlank
    @NotNull
    @Column
    private String filename;

    @NotBlank
    @NotNull
    @Column
    private String hash;

    @NotBlank
    @NotNull
    @Column
    private String path;

    @Column(name = "registration_time")
    private Timestamp registrationTime;

    @Column(name = "last_update")
    private Timestamp lastUpdate;


    @Override
    public TestVector dto() {
        TestVector tv = new TestVector();
        tv.setId(id);
        tv.setUrl(url);
        tv.setFileName(filename);
        tv.setHash(hash);
        tv.setPath(path);
        tv.setRegistrationTime(registrationTime);
        tv.setLastUpdate(lastUpdate);
        return tv;
    }

    public static class Specification extends SimpleSearchSpecification<TestVectorEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}