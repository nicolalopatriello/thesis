package it.nicolalopatriello.thesis.core;


import it.nicolalopatriello.thesis.common.spring.jpa.JpaSpecificationExecutorWithProjectionImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
@Log4j
// @EnableScheduling
public class CoreApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(CoreApplication.class);
		System.err.println("AAAAAAAAAAAAAAAAAA");
		springApplication.run();
	}
}
