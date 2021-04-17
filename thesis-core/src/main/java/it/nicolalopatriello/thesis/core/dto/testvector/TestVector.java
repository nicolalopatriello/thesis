package it.nicolalopatriello.thesis.core.dto.testvector;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.TestVectorEntity;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;

@Log4j
@Data
public class TestVector extends DTO {

    private Long id;
    private String url;
    private String fileName;
    private String hash;
    private String path;
    private Timestamp registrationTime;
    private Timestamp lastUpdate;

    public TestVectorEntity to() {
        TestVectorEntity testVector = new TestVectorEntity();
        testVector.setId(id);
        testVector.setUrl(url);
        testVector.setFilename(fileName);
        testVector.setHash(hash);
        testVector.setPath(path);
        testVector.setRegistrationTime(registrationTime);
        testVector.setLastUpdate(lastUpdate);
        return testVector;
    }


    public static TestVector from(TestVectorEntity testVector) {
        TestVector dto = new TestVector();
        dto.setId(testVector.getId());
        dto.setUrl(testVector.getUrl());
        dto.setFileName(testVector.getFilename());
        dto.setHash(testVector.getHash());
        dto.setPath(testVector.getPath());
        dto.setRegistrationTime(testVector.getRegistrationTime());
        dto.setLastUpdate(testVector.getLastUpdate());
        return dto;
    }


}
