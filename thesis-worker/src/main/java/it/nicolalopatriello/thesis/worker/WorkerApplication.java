package it.nicolalopatriello.thesis.worker;

import it.nicolalopatriello.thesis.core.dto.worker.WorkerJobResponse;
import it.nicolalopatriello.thesis.exception.HttpRequestException;
import it.nicolalopatriello.thesis.worker.client.ThesisCoreHttpClient;

import java.util.Optional;

public class WorkerApplication {

    public static void main(String[] args) throws HttpRequestException, InterruptedException {
        ThesisCoreHttpClient thesisCoreHttpClient = new ThesisCoreHttpClient();
        while (true) {
            Thread.sleep(5000);
            Optional<WorkerJobResponse> optionalWorkerJobResponse = thesisCoreHttpClient.findJob();
            if (optionalWorkerJobResponse.isPresent()) {
                WorkerJobResponse wj = optionalWorkerJobResponse.get();
                LocalRepoHandler localRepoHandler = new LocalRepoHandler();
                try {
                    String path = localRepoHandler.getLocalPath(
                            wj.getRepositoryUrl(),
                            wj.getRepositoryUsername(),
                            wj.getRepositoryPassword(),
                            wj.getRepositoryBranch()
                    );
                    System.err.println(path);
                } catch (Exception e) {
                    System.err.println("Error " + e);
                }

            }
        }
    }
}
