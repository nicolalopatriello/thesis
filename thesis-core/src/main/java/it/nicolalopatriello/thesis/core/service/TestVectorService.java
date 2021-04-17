package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.NotFoundException;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorUpdateRequest;

import java.util.List;
import java.util.Optional;


public interface TestVectorService {

    Optional<TestVector> findById(Long id);

    Optional<TestVector> findByUrl(String url);

    List<TestVector> findAll();

    TestVector create(TestVectorCreateRequest dto) throws NotFoundException;

    TestVector update(TestVectorUpdateRequest dto) throws NotFoundException;


}
