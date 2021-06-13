package it.nicolalopatriello.thesis.runner;

import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.common.dto.RunnerResponse;
import it.nicolalopatriello.thesis.common.utils.TimeUtils;
import it.nicolalopatriello.thesis.runner.client.CoreHttpClient;
import lombok.extern.log4j.Log4j;

import java.util.Optional;

@Log4j
public class RunnerApplication {
//    public static void main(String[] args) throws Exception {
//        RunnerEngine runnerEngine = new RunnerEngine();
////        RunnerJobResponse.RepositoryCredentials r = new RunnerJobResponse.RepositoryCredentials();
////        r.setRepositoryUsername("nlopatriello");
////        r.setRepositoryPassword("intothewild_90");
////        DockerInspectServiceImpl d = new DockerInspectServiceImpl();
////        String inspect = d.inspect("repository.v2.moon-cloud.eu:4567",
////                "probes/ssh-scan:latest",
////                "gem query --local",
////                r
////        );
////        System.err.println("inspect output: " + inspect);
//
//        RunnerJobResponse j = new RunnerJobResponse();
//        RunnerJobResponse.RepositoryCredentials r = new RunnerJobResponse.RepositoryCredentials();
//        r.setRepositoryUsername("nlopatriello");
//        r.setRepositoryPassword("intothewild_90");
//        j.setCredentials(r);
//
//        Recipe recipe = new Recipe();
//        Recipe.Item i = new Recipe.Item();
//        i.setWatcherType(WatcherType.SIMPLE_DOCKER_INSPECT);
//        Object o = new Object();
//        i.setArgs();
//        recipe.setItems(List.of(i));
//        j.setRecipe(reù);
//        RunnerResponse response = runnerEngine.accept(j);
//    }

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
            TimeUtils.awaitFor(RunnerProperties.awaitInterval());
        }
    }


}
