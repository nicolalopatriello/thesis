package it.nicolalopatriello.thesis.core.scheduler;

import it.nicolalopatriello.thesis.common.GitRepoHandler;
import it.nicolalopatriello.thesis.common.exception.FolderCreationException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Log4j
public class VulnerabilitiesDBSync {

    private final GitRepoHandler handler = new GitRepoHandler();


    @Value("${app.cveList.dir}")
    private String cveListDirectory;

    @Value("${app.vulnerabilities.python.dir}")
    private String pythonVulnerabilitiesDirectory;


    @Scheduled(fixedDelayString = "${app.vulnerabilitiesDB.scheduledTime}")
    public void fetch() throws IOException {
        File cvePath = new File(cveListDirectory);
        if (!cvePath.exists() && cvePath.mkdirs())
            throw new FolderCreationException(cvePath);
        String cveSha = handler.fetch(cvePath, "https://github.com/CVEProject/cvelist.git", "master", null);
        log.debug("New CVE Database SHA is " + cveSha);

        File pythonVul = new File(pythonVulnerabilitiesDirectory);
        if (!pythonVul.exists() && pythonVul.mkdirs())
            throw new FolderCreationException(pythonVul);
        String pythonSha = handler.fetch(pythonVul, "https://github.com/pyupio/safety-db.git", "master", null);
        log.debug("New Python Database dependencies SHA is " + pythonSha);
    }
}
