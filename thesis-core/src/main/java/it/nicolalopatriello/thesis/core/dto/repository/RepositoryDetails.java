package it.nicolalopatriello.thesis.core.dto.repository;

import it.nicolalopatriello.thesis.common.dto.Metric;
import it.nicolalopatriello.thesis.core.dto.Vulnerability;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryDetails {
    private Long id;
    private String url;
    private String branch;
    private String lastCommitSha;
    private String summary;
    private Long runnerFinishedAt;
    private Long minutesWatchersInterval;
    private List<DependencyWithVulnerabilities> dependencies;
    private List<Metric> metrics;

    @Getter
    @Setter
    public static class DependencyWithVulnerabilities {
        private Long id;
        private String name;
        private String version;
        private String programmingLanguage;
        private List<Vulnerability> vulnerabilities;
    }
}
