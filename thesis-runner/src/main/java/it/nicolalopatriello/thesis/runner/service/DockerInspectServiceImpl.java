package it.nicolalopatriello.thesis.runner.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.messages.RegistryAuth;
import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.UUID;

@Log4j
public class DockerInspectServiceImpl implements DockerInspectService {
    @Override
    public String inspect(String repositoryAddress, String image, String command, RunnerJobResponse.RepositoryCredentials creds) {
        RegistryAuth registryAuth = RegistryAuth.create(
                creds.getRepositoryUsername(),
                creds.getRepositoryPassword(),
                null,
                repositoryAddress,
                null,
                null);
        StringBuilder output = new StringBuilder();
        try {
            DefaultDockerClient docker = DefaultDockerClient.fromEnv().dockerAuth(false).registryAuth(registryAuth).build();
            docker.auth(registryAuth);
            String serverWithImage = (String.format("%s/%s", repositoryAddress, image));
            log.info("Pull image from " + serverWithImage);
            docker.pull(serverWithImage);
            String fileName = UUID.randomUUID() + ".tmp";
            String cmd = String.format("docker run %s \"%s\" > /tmp/%s", serverWithImage, command, fileName);
            log.info("Command to execute " + cmd);
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
            p.waitFor();
            BufferedReader br = new BufferedReader(new FileReader("/tmp/" + fileName));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                output.append(currentLine).append("\n");
            }
            File f = new File("/tmp/" + fileName);
            f.delete();
        } catch (Exception e) {
            log.error("Cannot inspect container " + e.getMessage());
            e.printStackTrace();
        }
        return output.toString();
    }
}
