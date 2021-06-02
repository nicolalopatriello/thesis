package it.nicolalopatriello.thesis.runner;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j
public class RunnerApplication {


    public static void main(String[] args) throws IOException, ParseException {

    }

//    public static void main(String[] args) {
//        RunnerEngine runnerEngine = new RunnerEngine();
//        CoreHttpClient client = new CoreHttpClient();
//
//        boolean inAwait = false;
//        while (true) {
//            try {
//                Optional<RunnerJobResponse> job = client.findJob();
//                if (job.isPresent()) {
//                    RunnerJobResponse j = job.get();
//                    log.info("Job for " + j.getRepositoryUrl());
//                    inAwait = false;
//                    RunnerResponse response = runnerEngine.accept(j);
//                    log.debug("Watcher response ready. Send back. Watchers len: " + response.getWatchers().size());
//                    client.send(response);
//                } else {
//                    log.debug("No job available");
//                    if (!inAwait)
//                        log.info("No job available retry on each " + (RunnerProperties.awaitInterval() / 1000) + " sec");
//                    inAwait = true;
//                }
//            } catch (Exception e) {
//                log.error("Cannot fetch job. Retry in " + (RunnerProperties.awaitInterval() / 1000) + " sec");
//                log.error(e.getMessage(), e);
//            }
//
//            awaitFor();
//        }
//    }


    private static void awaitFor() {
        try {
            Thread.sleep(RunnerProperties.awaitInterval());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
