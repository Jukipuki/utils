package com.jukipuki.enums.lookup;

import static com.jukipuki.enums.EnumUtils.getFieldValue;
import static com.jukipuki.enums.EnumUtils.getAnnotatedField;

public interface Indexable<T> {

    default T getKey() {
        return getFieldValue(this, getAnnotatedField(getClass(), Key.class));
    }

    default String getName() {
        return getFieldValue(this, getAnnotatedField(getClass(), Name.class));
    }

}
