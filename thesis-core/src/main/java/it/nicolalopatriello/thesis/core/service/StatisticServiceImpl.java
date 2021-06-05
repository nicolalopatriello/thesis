package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.core.dto.Statistic;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.DependencyRepository;
import it.nicolalopatriello.thesis.core.repos.MetricRepository;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.repos.VulnerabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    ThesisRepositoryRepository thesisRepositoryRepository;

    @Autowired
    MetricRepository metricRepository;

    @Autowired
    VulnerabilityRepository vulnerabilityRepository;

    @Autowired
    DependencyRepository dependencyRepository;

    @Override
    public Statistic find(String username) {
        Statistic statistic = new Statistic();
        List<RepositoryEntity> byOwner = thesisRepositoryRepository.findByOwner(username);
        if (byOwner.isEmpty())
            return statistic;
        statistic.addRepo(byOwner.size());
        byOwner.forEach(repo -> {
            statistic.addMetrics(metricRepository.findByRepositoryId(repo.getId()).size());
            dependencyRepository.findByRepositoryId(repo.getId()).forEach(dep -> {
                statistic.addVulns(vulnerabilityRepository.findByDependencyId(dep.getId()).size());
            });
        });
        return statistic;
    }
}
