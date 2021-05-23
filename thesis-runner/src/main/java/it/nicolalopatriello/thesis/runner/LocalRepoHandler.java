package it.nicolalopatriello.thesis.runner;

import it.nicolalopatriello.thesis.common.dto.RunnerJobResponse;
import it.nicolalopatriello.thesis.runner.exception.GetLatestShaException;
import lombok.extern.log4j.Log4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

@Log4j
public class LocalRepoHandler {

    public static String getSha(File f, String url, String branch, RunnerJobResponse.RepositoryCredentials credentials) {
        String[] list = f.list();
        boolean isFolderEmpty = list != null && list.length == 0;
        try {
            if (isFolderEmpty) {
                log.debug("Folder is empty. Clone repository from remote source");
                Git.cloneRepository()
                        .setURI(url)
                        .setBranch(branch)
                        .setDirectory(f)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(credentials.getRepositoryUsername(), credentials.getRepositoryPassword()))
                        .call();
            } else {
                log.debug("Folder already exist. Pull new code from remote repository");
                Repository repository = new RepositoryBuilder()
                        .setGitDir(new File(f, ".git"))
                        .build();
                Git git = new Git(repository);
                git.pull()
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(credentials.getRepositoryUsername(), credentials.getRepositoryPassword()))
                        .setRemoteBranchName(branch)
                        .call();
            }

            Repository repository = new RepositoryBuilder()
                    .setGitDir(new File(f, ".git"))
                    .build();
            Ref head = repository.getAllRefs().get("HEAD");
            return head.getObjectId().getName();
        } catch (Exception e) {
            log.error("Cannot clone or pull code from remote source.");
            throw new GetLatestShaException(e.getMessage());
        }
    }
}
