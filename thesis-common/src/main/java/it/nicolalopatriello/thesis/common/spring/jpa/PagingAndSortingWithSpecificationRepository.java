package it.nicolalopatriello.thesis.common.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by Domenico Grieco <domenico.grieco@gfmintegration.it> on 12/3/17.
 */
@NoRepositoryBean
public interface PagingAndSortingWithSpecificationRepository<E, K extends Serializable> extends JpaRepository<E, K>, JpaSpecificationExecutorWithProjection<E> {
}
