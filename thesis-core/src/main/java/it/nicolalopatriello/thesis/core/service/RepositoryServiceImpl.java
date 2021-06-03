package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.Metric;
import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.Vulnerability;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryDetails;
import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.entities.MetricEntity;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.DependencyRepository;
import it.nicolalopatriello.thesis.core.repos.MetricRepository;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.repos.VulnerabilityRepository;
import it.nicolalopatriello.thesis.core.utils.JwtUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;

    @Autowired
    private DependencyRepository dependencyRepository;

    @Autowired
    private VulnerabilityRepository vulnerabilityRepository;

    @Autowired
    private MetricRepository metricRepository;

    @Override
    public RepositoryCreateResponse create(JwtUser user, RepositoryCreateRequest repositoryCreateRequest) {
        RepositoryEntity repositoryEntity = new RepositoryEntity();
        repositoryEntity.setUrl(repositoryCreateRequest.getUrl());
        repositoryEntity.setUsername(repositoryCreateRequest.getUsername());
        repositoryEntity.setPassword(repositoryCreateRequest.getPassword());
        repositoryEntity.setBranch(repositoryCreateRequest.getBranch());
        if (repositoryCreateRequest.getRecipe() != null)
            repositoryEntity.setRecipe(repositoryCreateRequest.getRecipe().toString());
        repositoryEntity.setMinutesWatchersInterval(repositoryCreateRequest.getMinutesWatchersInterval());
        repositoryEntity.setOwner(user.getUsername());
        return RepositoryCreateResponse.from(thesisRepositoryRepository.save(repositoryEntity));
    }

    @Override
    public List<RepositoryEntity> findByRunnerIdIsNull() {
        return thesisRepositoryRepository.findByRunnerIdIsNull();
    }

    @Override
    public List<RepositoryEntity> findByOwner(String username) {
        return thesisRepositoryRepository.findByOwner(username);
    }

    @Override
    public void save(RepositoryEntity r) {
        thesisRepositoryRepository.save(r);
    }


    @Override
    public Optional<RepositoryEntity> findById(Long repositoryId) {
        return thesisRepositoryRepository.findById(repositoryId);
    }

    @Override
    public RepositoryDetails findByIdWithDetails(Long id) throws NotFoundException {
        Optional<RepositoryEntity> byId = thesisRepositoryRepository.findById(id);
        if (byId.isPresent()) {
            RepositoryEntity repositoryEntity = byId.get();
            List<DependencyEntity> byRepositoryId = dependencyRepository.findByRepositoryId(id);
            List<Metric> metrics = metricRepository
                    .findByRepositoryId(id)
                    .stream()
                    .map(MetricEntity::dto)
                    .collect(Collectors.toList());

            List<RepositoryDetails.DependencyWithVulnerabilities> list = byRepositoryId.stream().map(dep -> {
                vulnerabilityRepository.findByDependencyId(dep.getId());
                RepositoryDetails.DependencyWithVulnerabilities depWithVuln = new RepositoryDetails.DependencyWithVulnerabilities();
                depWithVuln.setId(dep.getId());
                depWithVuln.setName(dep.getName());
                depWithVuln.setVersion(dep.getVersion());
                depWithVuln.setProgrammingLanguage(dep.getProgrammingLanguage());
                depWithVuln.setVulnerabilities(vulnerabilityRepository.findByDependencyId(dep.getId()).stream().map(Vulnerability::from).collect(Collectors.toList()));
                return depWithVuln;
            }).collect(Collectors.toList());

            RepositoryDetails details = new RepositoryDetails();
            details.setBranch(repositoryEntity.getBranch());
            details.setId(repositoryEntity.getId());
            details.setLastCommitSha(repositoryEntity.getLastCommitSha());
            details.setUrl(repositoryEntity.getUrl());
            if (repositoryEntity.getRunnerFinishedAt() != null)
                details.setRunnerFinishedAt(repositoryEntity.getRunnerFinishedAt().getTime());
            details.setMinutesWatchersInterval(repositoryEntity.getMinutesWatchersInterval());
            details.setDependencies(list);
            details.setMetrics(metrics);

            return details;
        }
        throw new NotFoundException();
    }

    @Override
    @Transactional
    public void delete(Long repositoryId) throws NotFoundException, BadRequestException {
        Optional<RepositoryEntity> byId = thesisRepositoryRepository.findById(repositoryId);
        if (byId.isPresent()) {
            if (byId.get().getRunnerStartedAt() != null)
                throw new BadRequestException();
            List<DependencyEntity> dependencies = dependencyRepository.findByRepositoryId(repositoryId);
            for (DependencyEntity d : dependencies) {
                vulnerabilityRepository.deleteByDependencyId(d.getId());
            }
            for (DependencyEntity d : dependencies) {
                dependencyRepository.deleteById(d.getId());
            }
            thesisRepositoryRepository.deleteById(repositoryId);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void clearReferences(Long repositoryId) {
        Optional<RepositoryEntity> byId = thesisRepositoryRepository.findById(repositoryId);
        if (byId.isPresent()) {
            List<DependencyEntity> dependencies = dependencyRepository.findByRepositoryId(repositoryId);
            List<MetricEntity> metrics = metricRepository.findByRepositoryId(repositoryId);
            for (MetricEntity m : metrics) {
                metricRepository.deleteById(m.getId());
            }
            for (DependencyEntity d : dependencies) {
                vulnerabilityRepository.deleteByDependencyId(d.getId());
            }
            for (DependencyEntity d : dependencies) {
                dependencyRepository.deleteById(d.getId());
            }
        }
    }


}
