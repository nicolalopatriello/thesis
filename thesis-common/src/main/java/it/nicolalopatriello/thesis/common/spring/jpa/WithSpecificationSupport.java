package it.nicolalopatriello.thesis.common.spring.jpa;

public interface WithSpecificationSupport<E> {
    E build(Object criteriaValue);
}
