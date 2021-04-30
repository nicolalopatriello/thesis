package it.nicolalopatriello.thesis.core.dto;

import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import lombok.Getter;
import org.kohsuke.github.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class LastRepoUpdate {

    private Timestamp timestamp;
    private final static Pattern REPO_NAME_PATTERN = Pattern.compile("https://github.com\\/(?<REPO>.*)\\..*");


    private static Optional<String> getRepoName(String completeUrl) {
        Matcher matcher = REPO_NAME_PATTERN.matcher(completeUrl.toLowerCase());
        String filename = null;
        if (matcher.find()) {
            filename = matcher.group("REPO");
        }
        return Optional.ofNullable(filename);
    }

    public LastRepoUpdate(Gitrace gitrace) throws IOException {
        switch (gitrace.getGitProvider()) {
            case GITHUB:
                GitHub github = GitHub.connect();

                //    GitHub github = new GitHubBuilder().withOAuthToken("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").build();
                Optional<String> repoNameOpt = getRepoName(gitrace.getGitRepoUrl());
                if (repoNameOpt.isPresent()) {
                    GHRepository repo = github.getRepository(repoNameOpt.get());
                    PagedIterable<GHCommit> pagedIterable = repo.listCommits();
                    Optional<GHCommit> optionalGHCommit = pagedIterable.toList().stream().findFirst();
                    if (optionalGHCommit.isPresent()) {
                        Date date = optionalGHCommit.get().getCommitDate();
                        this.timestamp = new Timestamp(date.getTime());
                    }
                } else {
                    this.timestamp = null;
                }
                break;
            case GITLAB:
                break;
        }
    }
}
