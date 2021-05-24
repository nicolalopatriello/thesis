package it.nicolalopatriello.thesis.core;


import it.nicolalopatriello.thesis.core.jpa.JpaSpecificationExecutorWithProjectionImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
@Log4j
@EnableScheduling
public class CoreApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CoreApplication.class);
        springApplication.run();
    }
}
