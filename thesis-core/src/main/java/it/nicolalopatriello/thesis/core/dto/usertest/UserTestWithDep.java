package it.nicolalopatriello.thesis.core.dto.usertest;


import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class UserTestWithDep extends DTO {

    private String url;
    private String description;
    private Timestamp createdAt;
    private List<Gitrace> gitraceDep;
    private List<TestVector> testVectorDep;

}
