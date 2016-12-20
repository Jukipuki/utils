package com.jukipuki.enums;

import com.jukipuki.enums.lookup.Indexable;
import com.jukipuki.enums.lookup.Key;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static java.util.Arrays.asList;

public class EnumUtils {

    public static <T, E extends Indexable<T>> Field getAnnotatedField(Class<E> type, Class<? extends Annotation> annotation) {
        return asList(type.getDeclaredFields())
                .stream()
                .filter(field -> field.isAnnotationPresent(annotation))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("No fields annotated with " + annotation.getSimpleName() + " found in " + type.getSimpleName()));
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T getFieldValue(E instance, Field field) {
        try {
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
