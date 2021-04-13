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
    private String fileName;
    private String hash;
    private Timestamp lastChanged;
    private Timestamp lastModifiledFileTs;
    private String path;
    private Timestamp timestamp;
    private String url;

    public TestVectorEntity to(String username) {
        TestVectorEntity testVector = new TestVectorEntity();
        testVector.setId(id);
        testVector.setFilename(fileName);
        testVector.setHash(hash);
        testVector.setLastChanged(lastChanged);
        testVector.setLastModifiledFileTs(lastModifiledFileTs);
        testVector.setPath(path);
        testVector.setTimestamp(timestamp);
        testVector.setUrl(url);

        return testVector;
    }


    public static TestVector from(TestVectorEntity testVector) {
        TestVector dto = new TestVector();
        dto.setId(testVector.getId());
        dto.setFileName(testVector.getFilename());
        dto.setHash(testVector.getHash());
        dto.setLastChanged(testVector.getLastChanged());
        dto.setLastModifiledFileTs(testVector.getLastModifiledFileTs());
        dto.setPath(testVector.getPath());
        dto.setTimestamp(testVector.getTimestamp());
        dto.setUrl(testVector.getUrl());
        return dto;
    }


}
