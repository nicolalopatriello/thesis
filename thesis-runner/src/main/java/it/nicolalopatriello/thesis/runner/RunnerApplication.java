package it.nicolalopatriello.thesis.runner;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.extern.log4j.Log4j;

import java.io.*;

@Log4j
public class RunnerApplication {
    static DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    static DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .sslConfig(config.getSSLConfig())
            .maxConnections(100)
            .build();
    static DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);

    public static String runExec(String containerId, String... cmd) {
       String s = null;

        ExecCreateCmdResponse exec = dockerClient.execCreateCmd(containerId)
                .withCmd(cmd)
                .withTty(true)
                .withAttachStdout(true)
                .withAttachStdin(true)
                .exec();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStream errorStream = new ByteArrayOutputStream();
            dockerClient.execStartCmd(exec.getId()).withDetach(false)
                    .exec(new ExecStartResultCallback(outputStream, errorStream)).awaitCompletion();
            s = outputStream.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;

    }


    public static String runContainer(String... cmd) {
        CreateContainerResponse docker_image = dockerClient.createContainerCmd("busybox:uclibc")
                .withCmd(cmd)
                .exec();
        String containerId = docker_image.getId();
        dockerClient.startContainerCmd(containerId).exec();
        return containerId;
    }

    public static void main(String[] args) {
        try {

            String containerId = runContainer("tail", "-f", "/dev/null");
            System.out.printf("Container %s is running%n", containerId);
            String resp = runExec(containerId, "ls");
            System.err.println(resp.substring(0 , 3));

        } catch (Exception e) {

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
