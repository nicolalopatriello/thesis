package it.nicolalopatriello.thesis.core.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface PagingAndSortingWithSpecificationRepository<E, K extends Serializable> extends JpaRepository<E, K>, JpaSpecificationExecutorWithProjection<E> {
}
