package it.nicolalopatriello.thesis.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Statistic {
    private int repositories;
    private int vulnerabilities;
    private int metrics;

    public Statistic() {
        this.repositories = 0;
        this.vulnerabilities = 0;
        this.metrics = 0;
    }

    public void addVulns(int n) {
        this.vulnerabilities += n;
    }

    public void addRepo(int n) {
        this.repositories += n;
    }

    public void addMetrics(int n) {
        this.metrics += n;
    }
}
