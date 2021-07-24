package it.nicolalopatriello.thesis.runner.service;

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
        StringBuilder output = new StringBuilder();
        try {
            String loginCmd = String.format("docker login %s --username %s --password %s",
                    repositoryAddress,
                    creds.getRepositoryUsername(),
                    creds.getRepositoryPassword());
            Process loginPrc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", loginCmd});
            output.append("Output of ").append("\"").append(command).append("\"").append(" command is: \n\n");
            loginPrc.waitFor();
            String pullImageCmd = String.format("docker pull %s/%s", repositoryAddress, image);
            log.debug("Pull image command: " + pullImageCmd);
            Process pullPrc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", loginCmd});
            pullPrc.waitFor();
            String serverWithImage = (String.format("%s/%s", repositoryAddress, image));
            log.info("Pull image from " + serverWithImage);
            String fileName = UUID.randomUUID() + ".tmp";
            String cmd = String.format("docker run %s \"%s\" > /tmp/%s", serverWithImage, command, fileName);
            log.info("Command to execute " + cmd);
            Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
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
