package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.repos.ThesisRepositoryRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        repositoryEntity.setOwner(user.getUsername());
        return RepositoryCreateResponse.from(thesisRepositoryRepository.save(repositoryEntity));
    }


}
