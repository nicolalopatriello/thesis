package it.nicolalopatriello.thesis.core;

import it.nicolalopatriello.thesis.core.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.WorkerEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.sql.Timestamp;

@Log4j
@Data
@ToString
public class Worker extends DTO {

    private Long id;
    private String secret;
    private Long registeredAt;

    public WorkerEntity to() {
        WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setSecret(secret);
        workerEntity.setRegisteredAt(new Timestamp(registeredAt));
        return workerEntity;
    }


    public static Worker from(WorkerEntity workerEntity) {
        Worker dto = new Worker();
        dto.setId(workerEntity.getId());
        dto.setSecret(workerEntity.getSecret());
        if (workerEntity.getRegisteredAt() != null)
            dto.setRegisteredAt(workerEntity.getRegisteredAt().getTime());
        return dto;
    }


}
