package it.nicolalopatriello.thesis.core.entities;

import it.nicolalopatriello.thesis.common.spring.dto.WithDTO;
import it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria;
import it.nicolalopatriello.thesis.common.spring.jpa.SimpleSearchSpecification;
import it.nicolalopatriello.thesis.core.Schema;
import it.nicolalopatriello.thesis.core.dto.connection.Connection;
import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "connection", schema = Schema.SCHEMA_NAME)
@Data
public class ConnectionEntity implements WithDTO<Connection> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column(name = "git_provider")
    private GitProvider gitProvider;

    @Column
    private String endpoint;

    @Column
    private String token;

    @Column
    private String name;

    @Column
    private String username;


    @Override
    public Connection dto() {
        Connection connection = new Connection();
        connection.setId(id);
        connection.setGitProvider(gitProvider);
        connection.setEndpoint(endpoint);
        connection.setToken(token);
        connection.setName(name);
        connection.setUsername(username);
        return connection;
    }

    public static class Specification extends SimpleSearchSpecification<ConnectionEntity> {
        public Specification(SearchCriteria criteria) {
            super(criteria);
        }
    }
}