package com.jukipuki.enums.lookup;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.compare;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Lookup {

    public static <E extends Enum<E>> E lookup(Class<E> type, Object... keys) {
        boolean isIndexed = type.isAnnotationPresent(Indexed.class);
        if (!isIndexed) {
            throw new UnsupportedOperationException(type.getSimpleName() + " is not indexed");
        }
        List<Field> keyFields = getKeyFields(type);
        if (keyFields.size() != keys.length) {
            throw new InvalidParameterException("Number of key values doesn't match number of keys");
        }
        return asList(type.getEnumConstants())
                .stream()
                .filter(e -> {
                    for (int i = 0; i < keys.length; i++) {
                        try {
                            Field field = keyFields.get(i);
                            field.setAccessible(true);
                            if (!field.get(e).equals(keys[i])) {
                                return false;
                            }
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }

                    }
                    return true;
                })
                .findFirst()
                .get();
    }

    private static <E extends Enum<E>> List<Field> getKeyFields(Class<E> type) {
        return asList(type.getDeclaredFields())
                .stream()
                .filter(field -> field.isAnnotationPresent(Key.class))
                .sorted((f1, f2) -> compare(getKeyOrder(f1), getKeyOrder(f2)))
                .collect(toList());
    }

    private static int getKeyOrder(Field field) {
        return field.getAnnotation(Key.class).order();
    }

}
