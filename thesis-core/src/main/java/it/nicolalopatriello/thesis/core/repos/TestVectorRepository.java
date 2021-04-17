package it.nicolalopatriello.thesis.core.repos;

import it.nicolalopatriello.thesis.common.spring.jpa.PagingAndSortingWithSpecificationRepository;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.entities.TestVectorEntity;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestVectorRepository extends PagingAndSortingWithSpecificationRepository<TestVectorEntity, Long> {
    List<TestVector> findByUrl(String url);
}
