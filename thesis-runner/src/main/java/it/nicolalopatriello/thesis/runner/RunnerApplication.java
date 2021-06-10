package it.nicolalopatriello.thesis.runner;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.RegistryAuth;
import lombok.extern.log4j.Log4j;

@Log4j
public class RunnerApplication {
    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {
        RegistryAuth registryAuth = RegistryAuth.create(
                "nlopatriello",
                "intothewild_90",
                "nicola.lopatriello@studenti.unimi.it",
                "repository.v2.moon-cloud.eu:4567",
                null,
                null);
        DefaultDockerClient docker = DefaultDockerClient.fromEnv().dockerAuth(false).registryAuth(registryAuth).build();
        docker.auth(registryAuth);
        try {
            docker.pull("repository.v2.moon-cloud.eu:4567/probes/ssh-scan:latest");
            String lscmd = "docker run repository.v2.moon-cloud.eu:4567/probes/ssh-scan:latest ls > a.tmp";
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", lscmd});
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
//            TimeUtils.awaitFor(RunnerProperties.awaitInterval());
//        }
//    }


}
