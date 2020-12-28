package com.application.shared.infrastructure.in_memory;

import com.application.shared.domain.criteria.Criteria;
import com.application.shared.domain.criteria.Filter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryUtils<T> {

    public List<T> criteria(List<T> data, Criteria criteria) {

        return criteria.filters().filters().stream()
                .flatMap(filter -> data.stream().filter(obj -> {
                    try {
                        return filter(obj, filter);
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return false;
                }))
                .collect(Collectors.toList());

    }

    private boolean filter(T obj, Filter filter) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = obj.getClass();
        Method value = clazz.getDeclaredMethod(filter.field().toString());

        Class<?> methodClass = value.invoke(obj).getClass().getSuperclass();
        Method v2 = methodClass.getDeclaredMethod("value");
        Object value3 =  v2.invoke(obj);

        boolean isOk =  value3.equals(filter.value());
        return isOk;
    }
}
