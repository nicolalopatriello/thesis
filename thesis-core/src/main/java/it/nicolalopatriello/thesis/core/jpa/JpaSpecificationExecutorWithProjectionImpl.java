package it.nicolalopatriello.thesis.core.jpa;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.nicolalopatriello.thesis.common.exception.ApplySpecificationToCriteriaException;
import it.nicolalopatriello.thesis.common.exception.ProjectionAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;


public class JpaSpecificationExecutorWithProjectionImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JpaSpecificationExecutorWithProjection<T> {

    private static final Logger log = LoggerFactory.getLogger(JpaSpecificationExecutorWithProjectionImpl.class);

    private final EntityManager entityManager;

    private final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    public JpaSpecificationExecutorWithProjectionImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    private static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        Long total = 0L;

        Long element;
        for (Iterator var3 = totals.iterator(); var3.hasNext(); total = total + (element == null ? 0L : element)) {
            element = (Long) var3.next();
        }

        return total;
    }


    @Override
    public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType, Pageable pageable) {
        TypedQuery<T> query = getQuery(spec, getDomainClass(), projectionType, pageable.getSort());
        return readPageWithProjection(spec, projectionType, pageable, query);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    protected <P, S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Class<P> projectionClass, Sort sort) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(domainClass);
        Root root = this.applySpecificationToCriteria(spec, domainClass, projectionClass, query);

        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }

        return this.entityManager.createQuery(query);
    }

    private <S, P, U extends T> Root<U> applySpecificationToCriteria(Specification<U> spec, Class<U> domainClass, Class<P> projection, CriteriaQuery<S> query) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");
        Root root = query.from(domainClass);

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        Map<Integer, Selection<?>> maps = extractProjectionFields(projection, root);

        if (!maps.isEmpty()) {
            List<Selection<?>> paths = Lists.newArrayList();
            for (int i = 0; i < maps.size(); i++) {
                Selection<?> obj = maps.get(i);
                if (obj == null)
                    throw new ApplySpecificationToCriteriaException("Missing projection element for idx=" + i + ". Check projection class " + projection + ".");
                paths.add(obj);
            }
            query.multiselect(paths);
            query.distinct(true);
        }

        return root;

    }

    private Optional<Path> extractProjectionCountFields(Class<?> projection, Root root) {
        Method[] methods = projection.getMethods();
        Map<Integer, Selection<?>> maps = Maps.newHashMap();
        for (Method f : methods) {
            if (f.getName().startsWith("getFields") && f.isAnnotationPresent(ProjectionOrder.class)) {
                ProjectionOrder p = f.getAnnotation(ProjectionOrder.class);
                String att = String.valueOf(f.getName().charAt(3)).toLowerCase() + f.getName().substring(4);
                f.setAccessible(true);
                if (maps.containsKey(p.value())) {
                    throw new ProjectionAlreadyExistsException("projection key " + p.value() + " already exists. Check projection class " + projection + ".");
                }
                if (p.isCountKey())
                    return Optional.ofNullable(root.get(att));
            }
        }
        return Optional.empty();
    }

    private <P> Map<Integer, Selection<?>> extractProjectionFields(Class<P> projection, Root root) {
        Method[] methods = projection.getMethods();
        Map<Integer, Selection<?>> maps = Maps.newHashMap();
        for (Method f : methods) {
            if (f.getName().startsWith("getFields") && f.isAnnotationPresent(ProjectionOrder.class)) {
                ProjectionOrder p = f.getAnnotation(ProjectionOrder.class);
                String att = String.valueOf(f.getName().charAt(3)).toLowerCase() + f.getName().substring(4);
                f.setAccessible(true);
                if (maps.containsKey(p.value())) {
                    throw new ProjectionAlreadyExistsException("projection key " + p.value() + " already exists. Check projection class " + projection + ".");
                }
                maps.put(p.value(), root.get(att));
            }
        }
        return maps;
    }

    @Override
    public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType, String namedEntityGraph, org.springframework.data.jpa.repository.EntityGraph.EntityGraphType type, Pageable pageable) {
        EntityGraph<?> entityGraph = this.entityManager.getEntityGraph(namedEntityGraph);
        if (entityGraph == null) {
            throw new IllegalArgumentException("Not found named entity graph -> " + namedEntityGraph);
        }
        TypedQuery<T> query = getQuery(spec, getDomainClass(), projectionType, pageable.getSort());
        query.setHint(type.getKey(), entityGraph);
        return readPageWithProjection(spec, projectionType, pageable, query);
    }

    @Override
    public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType, JpaEntityGraph dynamicEntityGraph, Pageable pageable) {
        TypedQuery<T> query = getQuery(spec, getDomainClass(), projectionType, pageable.getSort());
        Map<String, Object> entityGraphHints = new HashMap<>();
        entityGraphHints.putAll(Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, getDomainClass()));
        applyEntityGraphQueryHints(query, entityGraphHints);
        return readPageWithProjection(spec, projectionType, pageable, query);
    }

    private <R> Page<R> readPageWithProjection(Specification<T> spec, Class<R> projectionType, Pageable pageable, TypedQuery<T> query) {
        if (log.isDebugEnabled() && query.getHints() != null) {
            query.getHints().forEach((key, value) -> log.info("apply query hints -> {} : {}", key, value));
        }

        Class<T> domainClass = getDomainClass();
        Page<T> result = (pageable == null) ? new PageImpl<>(query.getResultList()) : readPage2(query, domainClass, projectionType, pageable, spec);
        return result.map(item -> projectionFactory.createProjection(projectionType, item));
    }

    private void applyEntityGraphQueryHints(Query query, Map<String, Object> hints) {
        for (Map.Entry<String, Object> hint : hints.entrySet()) {
            query.setHint(hint.getKey(), hint.getValue());
        }
    }

    //@Override
    protected <P, S extends T> Page<S> readPage2(TypedQuery<S> query, final Class<S> domainClass, final Class<P> projectionClass, Pageable pageable, final Specification<S> spec) {
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(getCountQuery(spec, domainClass, projectionClass)));
    }

    protected <P, U extends T> TypedQuery<Long> getCountQuery(final Specification<U> spec, final Class<U> domainClass, Class<P> projectionClass) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<U> u = applySpecificationToCriteria(spec, domainClass, projectionClass, query);
        Optional<Path> countKey = extractProjectionCountFields(projectionClass, u);
        if (countKey.isPresent())
            query.select(cb.countDistinct(countKey.get()));
        else
            query.select(cb.count(u));
        return entityManager.createQuery(query);

    }
}