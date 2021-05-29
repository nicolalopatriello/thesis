package it.nicolalopatriello.thesis.core;


import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;
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
    public static void main(String[] args) throws CveDetailsClientException {
        SpringApplication springApplication = new SpringApplication(CoreApplication.class);
        springApplication.run();//
    /*    CveDetailsClient c = new CveDetailsClient();
        CVEDetails cve = c.getCve("CVE-2021-1644");
        Cvss cvss = Cvss.fromVector(cve.getCvssVector());
        Score t = cvss.calculateScore();
        System.err.println(t.getBaseScore());*/

    }
}
