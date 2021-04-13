package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;
import it.nicolalopatriello.thesis.core.entities.TestVectorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;


public interface TestVectorService {

    Optional<TestVector> findById(Long id);

    List<TestVector> findAll(Specification<TestVectorEntity> specification, Pageable pageable);

    TestVector create(TestVectorCreateRequest dto) throws NotFoundException;

  //  TestVector update(Long id, UpdateJobExtractionProcessRequest dto) throws NotFoundException;


}
