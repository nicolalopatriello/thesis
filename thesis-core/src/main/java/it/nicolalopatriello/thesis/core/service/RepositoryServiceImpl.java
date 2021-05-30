package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.Vulnerability;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryDetails;
import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.DependencyRepository;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.repos.VulnerabilityRepository;
import it.nicolalopatriello.thesis.core.utils.JwtUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            details.setDependencies(list);

            return details;
        }
        throw new NotFoundException();
    }


}
