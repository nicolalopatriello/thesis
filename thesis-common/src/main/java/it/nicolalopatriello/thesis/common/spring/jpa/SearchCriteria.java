package it.nicolalopatriello.thesis.common.spring.jpa;

import java.io.Serializable;

public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    public static final String NULL = ":()";
    public static final String NOT_NULL = ":*";
    public static final String EQUAL = "::";
    public static final String NOT_EQUAL = "!:";
    public static final String GREATER_OR_EQ = ">";
    public static final String LESS_OR_EQ = "<";
    public static final String LIKE = "~:";
    public static final String IN = "_in_";
    public static final String IS = "_is_";
    public static final String BETWEEN = "_between_";
    public static final String START_WITH = "^:";


    private final String key;
    private final String operation;
    private final Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public SearchCriteria setKey(String key) {
        return new SearchCriteria(key, this.operation, this.value);
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }


    public static boolean isEmpty(SearchCriteria in) {
        return in instanceof EmptySearchCriteria;
    }

    public static SearchCriteria empty() {
        return new EmptySearchCriteria();
    }

    public static class EmptySearchCriteria extends SearchCriteria {
        public EmptySearchCriteria() {
            super(null, null, null);
        }
    }
}
