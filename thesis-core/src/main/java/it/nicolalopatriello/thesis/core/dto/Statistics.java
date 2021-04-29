package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.entities.UserTestDepGitraceEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@Data
@ToString
@Getter
@Setter
public class Statistics extends DTO {
    private int testVectors;
    private int notifications;
    private int gitraces;
    private int userTests;
}
