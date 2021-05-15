package it.nicolalopatriello.thesis.core.dto.connection;


import it.nicolalopatriello.thesis.common.spring.dto.DTO;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import it.nicolalopatriello.thesis.core.entities.ConnectionEntity;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j;


@Log4j
@Data
@ToString
public class Connection extends DTO {

    private Long id;
    private GitProvider gitProvider;
    private String endpoint;
    private String token;
    private String name;
    private String username;

    public ConnectionEntity to() {
        ConnectionEntity connectionEntity = new ConnectionEntity();
        connectionEntity.setId(id);
        connectionEntity.setGitProvider(gitProvider);
        connectionEntity.setEndpoint(endpoint);
        connectionEntity.setToken(token);
        connectionEntity.setName(name);
        connectionEntity.setUsername(username);
        return connectionEntity;
    }


    public static Connection from(ConnectionEntity connectionEntity) {
        Connection dto = new Connection();
        dto.setId(connectionEntity.getId());
        dto.setGitProvider(connectionEntity.getGitProvider());
        dto.setEndpoint(connectionEntity.getEndpoint());
        dto.setToken(connectionEntity.getToken());
        dto.setName(connectionEntity.getName());
        dto.setUsername(connectionEntity.getUsername());
        return dto;
    }


}
