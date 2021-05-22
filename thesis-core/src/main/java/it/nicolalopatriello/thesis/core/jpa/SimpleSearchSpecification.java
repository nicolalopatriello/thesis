package it.nicolalopatriello.thesis.core.jpa;

import it.nicolalopatriello.thesis.common.exception.OnEqualCriteriaException;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SimpleSearchSpecification<E> extends SearchSpecification<E> {

    public SimpleSearchSpecification(SearchCriteria criteria) {
        super(criteria);
    }


    @Override
    public Predicate onBetween(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        String[] array = criteria.getValue().toString().split(Pattern.quote("|"));
        if (array.length != 2)
            throw new UnsupportedBetweenParametersException("expected 2 values. actual=" + Arrays.toString(array));
        if (type == java.sql.Date.class)
            return builder.between(root.get(criteria.getKey()), new java.sql.Date(Long.parseLong(array[0])), new java.sql.Date(Long.parseLong(array[1])));

        if (type == java.util.Date.class)
            return builder.between(root.get(criteria.getKey()), new java.util.Date(Long.parseLong(array[0])), new java.util.Date(Long.parseLong(array[1])));

        if (type == java.sql.Timestamp.class)
            return builder.between(root.get(criteria.getKey()), new java.sql.Timestamp(Long.parseLong(array[0])), new java.sql.Timestamp(Long.parseLong(array[1])));

        if (type == Integer.class)
            return builder.between(root.get(criteria.getKey()), Integer.parseInt(array[0]), Integer.parseInt(array[1]));

        if (type == Double.class)
            return builder.between(root.get(criteria.getKey()), Double.parseDouble(array[0]), Double.parseDouble(array[1]));

        if (type == Long.class)
            return builder.between(root.get(criteria.getKey()), Long.parseLong(array[0]), Long.parseLong(array[1]));


        return builder.between(root.get(criteria.getKey()), array[0], array[1]);
    }


    @Override
    public Predicate onGreaterOrEq(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        if (type == java.sql.Date.class)
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), new java.sql.Date(Long.parseLong(criteria.getValue().toString())));

        if (type == java.util.Date.class)
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), new java.util.Date(Long.parseLong(criteria.getValue().toString())));

        if (type == java.sql.Timestamp.class)
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), new java.sql.Timestamp(Long.parseLong(criteria.getValue().toString())));

        if (type == Integer.class)
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), Integer.parseInt(criteria.getValue().toString()));

        if (type == Double.class)
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), Double.parseDouble(criteria.getValue().toString()));

        if (type == Long.class)
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), Long.parseLong(criteria.getValue().toString()));


        return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
    }

    @Override
    public Predicate onLessOrEq(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        if (type == java.sql.Date.class)
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), new java.sql.Date(Long.parseLong(criteria.getValue().toString())));

        if (type == java.util.Date.class)
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), new java.util.Date(Long.parseLong(criteria.getValue().toString())));

        if (type == java.sql.Timestamp.class)
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), new java.sql.Timestamp(Long.parseLong(criteria.getValue().toString())));

        if (type == Integer.class)
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), Integer.parseInt(criteria.getValue().toString()));

        if (type == Double.class)
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), Double.parseDouble(criteria.getValue().toString()));

        if (type == Long.class)
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), Long.parseLong(criteria.getValue().toString()));

        return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
    }

    //ignore case support
    @Override
    public Predicate onLike(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        if (root.get(criteria.getKey()).getJavaType() == String.class)
            return builder.like(
                    builder.lower(root.get(criteria.getKey())), "%" + ((String) criteria.getValue()).toLowerCase() + "%");
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
    }

    @Override
    public Predicate onStartWith(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        if (root.get(criteria.getKey()).getJavaType() == String.class)
            return builder.like(root.<String>get(criteria.getKey()), criteria.getValue() + "%");
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
    }

    @Override
    public Predicate onIn(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        if (type == String.class) {
            String[] list = criteria.getValue().toString().split(Pattern.quote("|"));
            Path<String> g = root.<String>get(criteria.getKey());
            return g.in(list);
        }
        throw new UnsupportedInCriteriaException(type);
    }

    @Override
    public Predicate onIs(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        if (Boolean.parseBoolean(criteria.getValue().toString()))
            return builder.isTrue(root.get(criteria.getKey()));
        else
            return builder.isFalse(root.get(criteria.getKey()));
    }

    @Override
    public Predicate onEqual(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        if (WithSpecificationSupport.class.isAssignableFrom(type))
            try {
                return builder.equal(root.get(criteria.getKey()), ((WithSpecificationSupport) type.newInstance()).build(criteria.getValue()));
            } catch (Exception e) {
                throw new OnEqualCriteriaException(e);
            }
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
    }

    @Override
    public Predicate onNotEqual(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
    }

    @Override
    public Predicate onNull(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        return builder.isNull(root.get(criteria.getKey()));
    }

    @Override
    public Predicate onNotNull(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchCriteria criteria) {
        return builder.isNotNull(root.get(criteria.getKey()));
    }

    public static class UnsupportedBetweenParametersException extends RuntimeException {
        public UnsupportedBetweenParametersException(String s) {
            super(s);
        }
    }
}