package it.nicolalopatriello.thesis.core.dto.connection;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import lombok.Data;
import lombok.extern.log4j.Log4j;


@Log4j
@Data
public class ConnectionLight extends DTO {

    private Long id;
    private GitProvider gitProvider;
    private String endpoint;
    private String name;

    public static ConnectionLight from(Connection connection) {
        ConnectionLight dto = new ConnectionLight();
        dto.setId(connection.getId());
        dto.setGitProvider(connection.getGitProvider());
        dto.setEndpoint(connection.getEndpoint());
        dto.setName(connection.getName());
        return dto;
    }


}
