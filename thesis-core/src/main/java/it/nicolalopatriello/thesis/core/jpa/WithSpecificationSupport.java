package it.nicolalopatriello.thesis.core.jpa;

public interface WithSpecificationSupport<E> {
    E build(Object criteriaValue);
}
