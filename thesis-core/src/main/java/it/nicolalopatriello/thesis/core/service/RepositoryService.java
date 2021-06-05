package it.nicolalopatriello.thesis.core.service;


import it.nicolalopatriello.thesis.common.exception.BadRequestException;
import it.nicolalopatriello.thesis.common.exception.EncryptionException;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryCreateResponse;
import it.nicolalopatriello.thesis.core.dto.repository.RepositoryDetails;
import it.nicolalopatriello.thesis.core.entities.RepositoryEntity;
import it.nicolalopatriello.thesis.core.utils.JwtUser;

import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

public interface RepositoryService {
    RepositoryCreateResponse create(JwtUser user, RepositoryCreateRequest repositoryCreateRequest) throws BadRequestException;

    List<RepositoryEntity> findByRunnerIdIsNull();

    List<RepositoryEntity> findByOwner(String username);

    void save(RepositoryEntity r);

    Optional<RepositoryEntity> findById(Long repositoryId);

    RepositoryDetails findByIdWithDetails(Long id) throws NotFoundException;

    void delete(Long repositoryId) throws NotFoundException, BadRequestException;

    void clearReferences(Long repositoryId);
}
