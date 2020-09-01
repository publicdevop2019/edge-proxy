package com.hw.shared.sql.clause;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SelectFieldCollectionContainsClause<T> extends WhereClause<T> {
    public SelectFieldCollectionContainsClause(String fieldName) {
        entityFieldName = fieldName;
    }


    private Predicate getExpression(String input, CriteriaBuilder cb, Root<T> root) {
        return cb.like(root.get(entityFieldName).as(String.class), "%" + input + "%");
    }

    // type:PROD.SALES.GENERAL
    // type:PROD$SALES$GENERAL
    @Override
    public Predicate getWhereClause(String query, CriteriaBuilder cb, Root<T> root) {
        //sort before search
        if (query.contains(".")) {
            Set<String> strings = new TreeSet<>(Arrays.asList(query.split("\\.")));
            List<Predicate> list1 = strings.stream().map(e -> getExpression(e, cb, root)).collect(Collectors.toList());
            return cb.or(list1.toArray(Predicate[]::new));
        }
        else if (query.contains("$")) {
            Set<String> strings = new TreeSet<>(Arrays.asList(query.split("\\$")));
            List<Predicate> list2 = strings.stream().filter(e -> e.contains("+")).map(e -> getExpression(e, cb, root)).collect(Collectors.toList());
            return cb.or(list2.toArray(Predicate[]::new));
        }else {
            return cb.like(root.get(entityFieldName).as(String.class), "%" + query + "%");
        }
    }
}
