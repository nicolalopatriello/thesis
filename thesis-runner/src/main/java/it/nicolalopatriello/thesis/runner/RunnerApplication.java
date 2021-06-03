package it.nicolalopatriello.thesis.runner;

import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.runner.client.CoreHttpClient;
import lombok.extern.log4j.Log4j;

import java.util.Optional;

@Log4j
public class RunnerApplication {
    public static void main(String[] args) {
        RunnerEngine runnerEngine = new RunnerEngine();
        CoreHttpClient client = new CoreHttpClient();

        boolean inAwait = false;
        while (true) {
            try {
                Optional<RunnerJobResponse> job = client.findJob();
                if (job.isPresent()) {
                    RunnerJobResponse j = job.get();
                    log.info("Job for " + j.getRepositoryUrl());
                    inAwait = false;
                    RunnerResponse response = runnerEngine.accept(j);
                    log.debug("Watcher response ready. Send back. Watchers len: " + response.getWatchers().size());
                    client.send(response);
                } else {
                    log.debug("No job available");
                    if (!inAwait)
                        log.info("No job available retry on each " + (RunnerProperties.awaitInterval() / 1000) + " sec");
                    inAwait = true;
                }
            } catch (Exception e) {
                log.error("Cannot fetch job. Retry in " + (RunnerProperties.awaitInterval() / 1000) + " sec");
                log.error(e.getMessage(), e);
            }

            awaitFor();
        }
    }


    private static void awaitFor() {
        try {
            Thread.sleep(RunnerProperties.awaitInterval());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
