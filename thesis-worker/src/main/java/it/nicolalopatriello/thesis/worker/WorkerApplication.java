package it.nicolalopatriello.thesis.worker;

import it.nicolalopatriello.thesis.common.dto.WorkerJobResponse;
import it.nicolalopatriello.thesis.worker.client.ThesisCoreHttpClient;
import it.nicolalopatriello.thesis.worker.exception.HttpRequestException;

import java.util.Optional;

public class WorkerApplication {

    public static void main(String[] args) throws HttpRequestException, InterruptedException {
        ThesisCoreHttpClient thesisCoreHttpClient = new ThesisCoreHttpClient();
        WorkerEngine workerEngine = new WorkerEngine();

        // workerEngine.accept();

        while (true) {
            Thread.sleep(5000);

            Optional<WorkerJobResponse> optionalWorkerJobResponse = thesisCoreHttpClient.findJob();
            if (optionalWorkerJobResponse.isPresent()) {

            }

          /*  if (optionalWorkerJobResponse.isPresent()) {
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

            }*/
        }
    }
}
