package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import it.nicolalopatriello.thesis.core.utils.JwtUser;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private ThesisRepositoryRepository thesisRepositoryRepository;


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


}
