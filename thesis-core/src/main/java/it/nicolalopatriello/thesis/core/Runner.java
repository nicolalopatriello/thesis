package it.nicolalopatriello.thesis.core;

import it.nicolalopatriello.thesis.core.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.RunnerEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;

@Log4j
@Data
@ToString
public class Runner extends DTO {

    private Long id;
    private String secret;
    private Long registeredAt;

    public RunnerEntity to() {
        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setSecret(secret);
        runnerEntity.setRegisteredAt(new Timestamp(registeredAt));
        return runnerEntity;
    }


    public static Runner from(RunnerEntity runnerEntity) {
        Runner dto = new Runner();
        dto.setId(runnerEntity.getId());
        dto.setSecret(runnerEntity.getSecret());
        if (runnerEntity.getRegisteredAt() != null)
            dto.setRegisteredAt(runnerEntity.getRegisteredAt().getTime());
        return dto;
    }


}
