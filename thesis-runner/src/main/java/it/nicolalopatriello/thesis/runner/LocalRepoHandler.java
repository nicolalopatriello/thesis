package it.nicolalopatriello.thesis.runner;

import it.nicolalopatriello.thesis.runner.exception.LocalRepoHandlerException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.stream.Collectors;

public class LocalRepoHandler {

    //todo from props
    private final static String LOCAL_FOLDER = "/tmp/";

    public String getLocalPath(String repositoryUrl, String username, String password, String branchName) throws LocalRepoHandlerException {
        sanitizeUrl(repositoryUrl);
        if (localRepoAlreadyExist(new File(LOCAL_FOLDER + sanitizeUrl(repositoryUrl)))) {
            try {
                Repository repository = new RepositoryBuilder()
                        .setGitDir(new File(LOCAL_FOLDER + sanitizeUrl(repositoryUrl)))
                        .build();
                Git git = new Git(repository);
                git.pull()
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                        .setRemoteBranchName(branchName)
                        .call();
            } catch (Exception e) {
                throw new LocalRepoHandlerException(String.format("[Pull] %s", e));
            }
        } else {
            try {
                Git.cloneRepository()
                        .setURI(repositoryUrl)
                        .setBranch(branchName)
                        .setDirectory(new File(LOCAL_FOLDER + sanitizeUrl(repositoryUrl)))
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                        .call();
            } catch (GitAPIException e) {
                throw new LocalRepoHandlerException(String.format("[Clone] %s", e));
            }
        }
        return sanitizeUrl(LOCAL_FOLDER + repositoryUrl);
    }

    private String sanitizeUrl(String url) {
        String allowed = "abcdefghijklmnopqrstuvwxyz0123456789";
        return url.codePoints()
                .mapToObj(c -> allowed.contains(String.valueOf((char) c)) ? String.valueOf((char) c) : "_")
                .collect(Collectors.joining());
    }

    private boolean localRepoAlreadyExist(File folderPath) {
        return folderPath.exists() && folderPath.isDirectory();
    }
}
