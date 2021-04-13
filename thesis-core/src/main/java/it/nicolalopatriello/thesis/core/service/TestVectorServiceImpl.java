package it.nicolalopatriello.thesis.core.service;

import com.google.common.collect.Maps;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;
import it.nicolalopatriello.thesis.core.entities.TestVectorEntity;
import it.nicolalopatriello.thesis.core.repos.TestVectorRepository;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j
public class TestVectorServiceImpl implements TestVectorService {

    @Autowired
    private TestVectorRepository repository;

    @Override
    public Optional<TestVector> findById(Long id) {
        return repository.findById(id).map(TestVectorEntity::dto);

    }

    @Override
    public TestVector create(TestVectorCreateRequest request) throws NotFoundException {
//        checkOrganizationMaxUsers(request.getMaxUsers());
        TestVectorEntity entity = new TestVectorEntity();
        entity.setUrl(request.getUrl());
        entity.setHash(request.getHash());
        entity.setFilename(request.getFileName());
        entity.setPath(request.getPath());
        //todo complete
        TestVector dto = repository.save(entity).dto();
        return dto;
    }

    @Override
    public List<TestVector> findAll(Specification<TestVectorEntity> specification, Pageable pageable) {
        return repository.findAll().stream().map(TestVectorEntity::dto).collect(Collectors.toList());
    }

}
