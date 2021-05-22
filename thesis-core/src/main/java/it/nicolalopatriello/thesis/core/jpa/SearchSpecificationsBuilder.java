package it.nicolalopatriello.thesis.core.jpa;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class SearchSpecificationsBuilder<E> {

    private static final Logger log = Logger.getLogger(SearchSpecificationsBuilder.class);
    private static final String REGEX = String.format("(\\w*)(%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s)(.*)",
            Pattern.quote(SearchCriteria.NULL),
            Pattern.quote(SearchCriteria.NOT_NULL),
            Pattern.quote(SearchCriteria.NOT_EQUAL),
            Pattern.quote(SearchCriteria.EQUAL),
            Pattern.quote(SearchCriteria.LESS_OR_EQ),
            Pattern.quote(SearchCriteria.GREATER_OR_EQ),
            Pattern.quote(SearchCriteria.LIKE),
            Pattern.quote(SearchCriteria.IN),
            Pattern.quote(SearchCriteria.IS),
            Pattern.quote(SearchCriteria.BETWEEN),
            Pattern.quote(SearchCriteria.START_WITH)
    );
    private static final Pattern pattern = Pattern.compile(REGEX);
    private final List<SearchCriteria> params;

    private SearchSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    /**
     * triple comma separated as
     * <field><operator><value>,...,<field><operator><value>
     * .
     * example:
     * userId::userTP,userId::userTP,uuid:PROVA
     *
     * @param search
     * @return
     */
    public static <E> SearchSpecificationsBuilder<E> of(String search) {
        SearchSpecificationsBuilder<E> builder = new SearchSpecificationsBuilder<>();
        StringBuilder stringBuilder = new StringBuilder();
        if (!validateSearch(search))
            return builder;

        String[] array = search.split(Pattern.quote(","));
        for (String line : array) {
            Matcher matcher = pattern.matcher(line.trim());
            if (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
                stringBuilder.append(String.format("(%s %s %s) AND ", matcher.group(1), matcher.group(2), matcher.group(3)));
            } else
                throw new UnsupportedQueryException(line);
        }
        log.info("specification query: " + stringBuilder.toString() + "TRUE");
        return builder;
    }

    private static boolean validateSearch(String search) {
        return search != null && !search.trim().isEmpty();
    }

    public static List<SearchCriteria> toSearchCriterias(String search) {
        List<SearchCriteria> list = Lists.newLinkedList();
        if (!validateSearch(search))
            return list;

        String[] array = search.split(Pattern.quote(","));
        for (String line : array) {
            Matcher matcher = pattern.matcher(line.trim());
            if (matcher.find()) {
                list.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            } else
                throw new UnsupportedQueryException(line);
        }

        return list;
    }


    public static <E> SearchSpecificationsBuilder<E> empty() {
        return new SearchSpecificationsBuilder<>();
    }


    public static <E> SearchSpecificationsBuilder<E> of(Collection<SearchCriteria> searchCriteria) {
        SearchSpecificationsBuilder<E> builder = new SearchSpecificationsBuilder<>();
        builder.params.addAll(new ArrayList<>(searchCriteria));
        return builder;
    }

    public SearchSpecificationsBuilder<E> with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public SearchSpecificationsBuilder<E> with(String key, String operation) {
        params.add(new SearchCriteria(key, operation, null));
        return this;
    }


    public SearchSpecificationsBuilder<E> with(String key, String operation, Optional<?> value) {
        params.add(new SearchCriteria(key, operation, value.orElse(null)));
        return this;
    }

    public Specification<E> build(Class<? extends SearchSpecification<E>> clazz) {
        if (params.isEmpty())
            return create(clazz, SearchCriteria.empty());

        List<Specification<E>> specs = params.stream().map(q -> create(clazz, q)).collect(Collectors.toList());
        Specification<E> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }

    private SearchSpecification<E> create(Class<? extends SearchSpecification<E>> clazz, SearchCriteria a) {
        try {
            return clazz.getConstructor(SearchCriteria.class).newInstance(a);
        } catch (Exception e) {
            throw new UnsupportedSearchSpecificationException(clazz);
        }

    }


    private static class UnsupportedSearchSpecificationException extends RuntimeException {
        public UnsupportedSearchSpecificationException(Class<?> clazz) {
            super(clazz != null ? clazz.getCanonicalName() : null);
        }
    }


    public static class UnsupportedQueryException extends RuntimeException {
        public UnsupportedQueryException(String m) {
            super(m);
        }
    }
}
