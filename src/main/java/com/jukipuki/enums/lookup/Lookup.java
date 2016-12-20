package com.jukipuki.enums.lookup;

import java.util.*;
import java.util.function.Predicate;

import static com.jukipuki.enums.EnumUtils.getAnnotatedField;
import static com.jukipuki.enums.EnumUtils.getFieldValue;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 * Lookup through {@link Enum} elements by defined key values
 * To apply this methods, {@link Enum} must implement {@link Indexable}
 * Key field must be annotated with {@link Key}
 */
@SuppressWarnings("unchecked")
public class Lookup {

    /**
     * Strict lookup
     * @param type - enum class
     * @param key - key value
     * @param <T> - key type
     * @param <E> - enum type
     * @return enum or throw {@link NoSuchElementException} if no element found
     */
    public static <T, E extends Indexable<T>> E lookupEnum(Class<E> type, T key) {
        return lookup(type, key, false, null, false);
    }

    /**
     * Optional lookup
     * @param type - enum class
     * @param key - key value
     * @param <T> - key type
     * @param <E> - enum type
     * @return enum or {@code null}
     */
    public static <T, E extends Indexable<T>> E lookupEnumNullable(Class<E> type, T key) {
        return lookupEnumNullable(type, key, null);
    }

    /**
     * Optional lookup
     * @param type - enum class
     * @param key - key value
     * @param defaultValue - default value in case when no elements found
     * @param <T> - key type
     * @param <E> - enum type
     * @return enum or {@code defaultValue}
     */
    public static <T, E extends Indexable<T>> E lookupEnumNullable(Class<E> type, T key, E defaultValue) {
        return lookup(type, key, true, defaultValue, false);
    }

    /**
     * Strict, case insensitive lookup
     * Applicable only for {@link String}, {@link String[]} and {@link Iterable<String>}
     * For all other types - behave like {@link #lookupEnum(Class, Object)}
     * @param type - enum class
     * @param key - key value
     * @param <T> - key type
     * @param <E> - enum type
     * @return enum or throw {@link NoSuchElementException} if no element found
     */
    public static <T, E extends Indexable<T>> E lookupEnumCaseInsensitive(Class<E> type, T key) {
        return lookup(type, key, false, null, true);
    }

    /**
     * Optional lookup
     * Applicable only for {@link String}, {@link String[]} and {@link Iterable<String>}
     * For all other types - behave like {@link #lookupEnumNullable(Class, Object)}
     * @param type - enum class
     * @param key - key value
     * @param <T> - key type
     * @param <E> - enum type
     * @return enum or {@code null}
     */
    public static <T, E extends Indexable<T>> E lookupEnumCaseInsensitiveNullable(Class<E> type, T key) {
        return lookupEnumCaseInsensitiveNullable(type, key, null);
    }

    /**
     * Optional lookup
     * Applicable only for {@link String}, {@link String[]} and {@link Iterable<String>}
     * For all other types - behave like {@link #lookupEnumNullable(Class, Object, Indexable)}
     * @param type - enum class
     * @param key - key value
     * @param <T> - key type
     * @param <E> - enum type
     * @return enum or {@code defaultValue}
     */
    public static <T, E extends Indexable<T>> E lookupEnumCaseInsensitiveNullable(Class<E> type, T key, E defaultValue) {
        return lookup(type, key, true, defaultValue, true);
    }

    private static <T, E extends Indexable<T>> E lookup(Class<E> type, T key, boolean nullable, E defaultValue, boolean caseInsensitive) {
        requireNonNull(key, "Key value cannot be null");
        Optional<E> e = asList(type.getEnumConstants())
                .stream()
                .filter(predicate(type, key, caseInsensitive))
                .findFirst();
        return nullable
                ? e.orElse(defaultValue)
                : e.orElseThrow(() -> new NoSuchElementException("No element found indexed by " + key + ", in " + type.getSimpleName()));
    }

    private static <T, E extends Indexable<T>> Predicate<E> predicate(Class<E> type, T key, boolean caseInsensitive) {
        return e -> {
            T value = getFieldValue(e, getAnnotatedField(type, Key.class));
            if (caseInsensitive) {
                if (value instanceof String && key instanceof String) {
                    return equalsStringCaseInsensitive((String) value, (String) key);
                }
                if (value instanceof String[] && key instanceof String[]) {
                    return equalsStringsArrayCaseInsensitive((String[]) value, (String[]) key);
                }
                if (value instanceof Iterable && key instanceof Iterable) {
                    if (((Iterable) value).iterator().next() instanceof String && ((Iterable) key).iterator().next() instanceof String) {
                        return equalsStringsIterableCaseInsensitive((Iterable<String>) value, (Iterable<String>) key);
                    }
                }
            }
            if (value instanceof Object[] && key instanceof Object[]) {
                return equalsObjectsArray((Object[]) value, (Object[]) key);
            }
            return equalsObject(value, key);
        };
    }

    private static boolean equalsObject(Object o1, Object o2) {
        return o1.equals(o2);
    }

    private static boolean equalsStringCaseInsensitive(String s1, String s2) {
        return s1.equalsIgnoreCase(s2);
    }

    private static boolean equalsObjectsArray(Object[] a1, Object[] a2) {
        return Arrays.equals(a1, a2);
    }

    private static boolean equalsStringsArrayCaseInsensitive(String[] a1, String[] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; i++) {
            if (!a1[i].equalsIgnoreCase(a2[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean equalsStringsIterableCaseInsensitive(Iterable<String> i1, Iterable<String> i2) {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        i1.forEach(l1::add);
        i2.forEach(l2::add);
        return equalsStringsArrayCaseInsensitive(l1.toArray(new String[l1.size()]), l2.toArray(new String[l2.size()]));
    }

}
