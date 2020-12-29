package com.application.shared.infrastructure.hibernate;

import com.application.shared.domain.criteria.Criteria;
import com.application.shared.domain.criteria.Filter;
import com.application.shared.domain.criteria.FilterOperator;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class HibernateCriteriaConverter<T> {
    private final CriteriaBuilder                                                 builder;
    private final HashMap<FilterOperator, BiFunction<Filter, Root<T>, Predicate>>
        predicateTransformers = new HashMap<FilterOperator, BiFunction<Filter, Root<T>, Predicate>>() {{
        put(FilterOperator.EQUAL, HibernateCriteriaConverter.this::equalsPredicateTransformer);
        put(FilterOperator.NOT_EQUAL, HibernateCriteriaConverter.this::notEqualsPredicateTransformer);
        put(FilterOperator.GT, HibernateCriteriaConverter.this::greaterThanPredicateTransformer);
        put(FilterOperator.LT, HibernateCriteriaConverter.this::lowerThanPredicateTransformer);
        put(FilterOperator.CONTAINS, HibernateCriteriaConverter.this::containsPredicateTransformer);
        put(FilterOperator.NOT_CONTAINS, HibernateCriteriaConverter.this::notContainsPredicateTransformer);
    }};
    public HibernateCriteriaConverter(CriteriaBuilder builder) {
        this.builder = builder;
    }

    public CriteriaQuery<T> convert(Criteria criteria, Class<T> aggregateClass) {
        CriteriaQuery<T> hibernateCriteria = builder.createQuery(aggregateClass);
        Root<T>          root              = hibernateCriteria.from(aggregateClass);

        hibernateCriteria.where(formatPredicates(criteria.filters().filters(), root));

        if (criteria.order().hasOrder()) {
            Path<Object> orderBy = root.get(criteria.order().orderBy().value());
            Order        order   = criteria.order().orderType().isAsc() ? builder.asc(orderBy) : builder.desc(orderBy);

            hibernateCriteria.orderBy(order);
        }

        return hibernateCriteria;
    }

    private Predicate[] formatPredicates(List<Filter> filters, Root<T> root) {
        List<Predicate> predicates = filters.stream().map(filter -> formatPredicate(
            filter,
            root
        )).collect(Collectors.toList());

        Predicate[] predicatesArray = new Predicate[predicates.size()];
        predicatesArray = predicates.toArray(predicatesArray);

        return predicatesArray;
    }

    private Predicate formatPredicate(Filter filter, Root<T> root) {
        BiFunction<Filter, Root<T>, Predicate> transformer = predicateTransformers.get(filter.operator());

        return transformer.apply(filter, root);
    }

    private Predicate equalsPredicateTransformer(Filter filter, Root<T> root) {

        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date")|| superclass.contains("DateValueObject")) {
            Date parsedDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate = formatter.parse(filter.value().value());
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.equal(root.get(filter.field().value()).get("value"), parsedDate);
            }
            return builder.equal(root.<Date>get(filter.field().value()), parsedDate);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.equal(rootObj, filter.value().value());
    }

    private Predicate notEqualsPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date")) {
            Date parsedDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate = formatter.parse(filter.value().value());
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.notEqual(root.get(filter.field().value()).get("value"), parsedDate);
            }
            return builder.notEqual(root.<Date>get(filter.field().value()), parsedDate);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.notEqual(rootObj, filter.value().value());
    }

    private Predicate greaterThanPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date") || superclass.contains("DateValueObject")) {
            Date parsedDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate = formatter.parse(filter.value().value());
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.greaterThan(root.<Date>get(filter.field().value()).get("value"), parsedDate);
            }
            return builder.greaterThan(root.<Date>get(filter.field().value()), parsedDate);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.greaterThan(rootObj, filter.value().value());
    }

    private Predicate lowerThanPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date") || superclass.contains("DateValueObject")) {
            Date parsedDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate = formatter.parse(filter.value().value());
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.lessThan(root.<Date>get(filter.field().value()).get("value"), parsedDate);
            }
            return builder.lessThan(root.<Date>get(filter.field().value()), parsedDate);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.lessThan(rootObj, filter.value().value());
    }

    private Predicate greaterEqualThanPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date") || superclass.contains("DateValueObject")) {
            Date parsedDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate = formatter.parse(filter.value().value());
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.greaterThanOrEqualTo(root.<Date>get(filter.field().value()).get("value"), parsedDate);
            }
            return builder.greaterThanOrEqualTo(root.<Date>get(filter.field().value()), parsedDate);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.greaterThanOrEqualTo(rootObj, filter.value().value());
    }

    private Predicate lowerEqualThanPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date") || superclass.contains("DateValueObject")) {
            Date parsedDate = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate = formatter.parse(filter.value().value());
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.lessThanOrEqualTo(root.<Date>get(filter.field().value()).get("value"), parsedDate);
            }
            return builder.lessThanOrEqualTo(root.<Date>get(filter.field().value()), parsedDate);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.lessThanOrEqualTo(rootObj, filter.value().value());
    }

    private Predicate containsPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.like(rootObj, String.format("%%%s%%", filter.value().value()));
    }

    private Predicate notContainsPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.notLike(rootObj, String.format("%%%s%%", filter.value().value()));
    }

    private Predicate betweenPredicateTransformer(Filter filter, Root<T> root) {
        Class<? extends Class<?>> rootType = root.get(filter.field().value()).type().getJavaType();
        Expression<String> rootObj;
        String superclass = rootType.getSuperclass().toString();
        String value1 = filter.value().toString().split(",")[0];
        String value2 = filter.value().toString().split(",")[1];
        if(rootType.toString().contains("java.lang.String") || rootType.isPrimitive()) {
            rootObj = root.get(filter.field().value());
        } else if(rootType.toString().contains("java.util.Date") || superclass.contains("DateValueObject")) {
            Date parsedDate1 = null, parsedDate2 = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parsedDate1 = formatter.parse(value1);
                parsedDate2 = formatter.parse(value2);
            }catch (Exception e) {
                System.out.println("Error parsing on order creator");
            }
            if( superclass.contains("DateValueObject")) {
                return builder.between(root.<Date>get(filter.field().value()).get("value"), parsedDate1, parsedDate2);
            }
            return builder.between(root.<Date>get(filter.field().value()), parsedDate1, parsedDate2);
        } else {
            rootObj = root.get(filter.field().value()).get("value");
        }
        return builder.between(rootObj, value1, value2);
    }
}
