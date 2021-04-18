package it.nicolalopatriello.thesis.common.spring.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import static it.nicolalopatriello.thesis.common.spring.jpa.SearchCriteria.*;

public abstract class SearchSpecification<E> implements Specification<E> {

    private SearchCriteria criteria;

    public SearchSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }


    public abstract Predicate onGreaterOrEq(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onLessOrEq(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onLike(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onIn(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onEqual(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onNotEqual(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onNull(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onNotNull(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onIs(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onStartWith(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);

    public abstract Predicate onBetween(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria);


    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (SearchCriteria.isEmpty(criteria))
            return null;

        Predicate predicate;
        switch (criteria.getOperation()) {
            case NULL:
                predicate = onNull(root, query, builder, criteria);
                break;

            case NOT_NULL:
                predicate = onNotNull(root, query, builder, criteria);
                break;

            case GREATER_OR_EQ:
                predicate = onGreaterOrEq(root, query, builder, criteria);
                break;

            case BETWEEN:
                predicate = onBetween(root, query, builder, criteria);
                break;

            case LESS_OR_EQ:
                predicate = onLessOrEq(root, query, builder, criteria);
                break;

            case LIKE:
                predicate = onLike(root, query, builder, criteria);
                break;

            case IS:
                predicate = onIs(root, query, builder, criteria);
                break;

            case START_WITH:
                predicate = onStartWith(root, query, builder, criteria);
                break;

            case IN:
                predicate = onIn(root, query, builder, criteria);
                break;

            case EQUAL:
                predicate = onEqual(root, query, builder, criteria);
                break;

            case NOT_EQUAL:
                predicate = onNotEqual(root, query, builder, criteria);
                break;

            default:
                throw new UnsupportedSearchOperatorException(criteria.getOperation());
        }


        return predicate;

    }

    public static class UnsupportedSearchOperatorException extends RuntimeException {
        public UnsupportedSearchOperatorException(String operation) {
            super(operation);
        }
    }

    public static class UnsupportedInCriteriaException extends RuntimeException {
        public UnsupportedInCriteriaException(Class<?> type) {
            super(type != null ? type.getCanonicalName() : null);
        }
    }


}
