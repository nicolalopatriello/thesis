package it.nicolalopatriello.thesis.core.service;

import com.google.common.collect.Maps;
import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorUpdateRequest;
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
    public Optional<TestVector> findByUrl(String url) {
        List<TestVector> list = repository.findByUrl(url);
        if(list != null && !list.isEmpty())
            return Optional.ofNullable(list.get(0));
        return Optional.empty();
    }

    @Override
    public Optional<TestVector> findById(Long id) {
        return repository.findById(id).map(TestVectorEntity::dto);
    }

    @Override
    public TestVector create(TestVectorCreateRequest request) throws NotFoundException {
        TestVectorEntity entity = new TestVectorEntity();
        entity.setUrl(request.getUrl());
        entity.setFilename(request.getFileName());
        entity.setHash(request.getHash());
        entity.setPath(request.getPath());
        entity.setRegistrationTime(new Timestamp(System.currentTimeMillis()));
        entity.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        TestVector dto = repository.save(entity).dto();
        return dto;
    }

    @Override
    public TestVector update(TestVectorUpdateRequest dto) throws NotFoundException {
        Optional<TestVectorEntity> tsOpt = repository.findById(dto.getId());
        if (!tsOpt.isPresent())
            throw new NotFoundException();
        TestVectorEntity entity = tsOpt.get();
        entity.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        entity.setHash(dto.getHash());
        TestVector t = repository.save(entity).dto();
        return t;
    }

    @Override
    public List<TestVector> findAll() {
        return repository.findAll().stream().map(TestVectorEntity::dto).collect(Collectors.toList());
    }

}
