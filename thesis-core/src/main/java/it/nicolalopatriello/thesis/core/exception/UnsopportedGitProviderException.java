package it.nicolalopatriello.thesis.core.exception;

import it.nicolalopatriello.thesis.core.dto.gitrace.GitProvider;

public class UnsopportedGitProviderException extends RuntimeException {
    public UnsopportedGitProviderException(GitProvider gitProvider) {
        super(gitProvider != null ? gitProvider.name() : null);
    }
}
