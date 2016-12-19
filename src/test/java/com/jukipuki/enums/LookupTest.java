package com.jukipuki.enums;

import com.jukipuki.enums.lookup.Indexed;
import com.jukipuki.enums.lookup.Key;
import com.jukipuki.enums.lookup.Lookup;

public class LookupTest {

    public static void main(String[] args) {
        TestEnum lookup = Lookup.lookup(TestEnum.class, 2);
        String value = lookup.getValue();
        TestMultiKeyEnum multiKeyEnum = Lookup.lookup(TestMultiKeyEnum.class, 2, "Dos");
        multiKeyEnum.getValue();
    }

    @Indexed
    enum TestEnum {

        FIRST(1, "First"),
        SECOND(2, "Second"),
        THIRD(3, "Third");

        @Key
        private final int key;
        private final String value;

        TestEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    @Indexed
    enum TestMultiKeyEnum {

        FIRST(1, "Uno", "First"),
        SECOND(2, "Dos", "Second"),
        THIRD(3, "Tres", "Third");

        @Key(order = 2)
        private final int key1;

        @Key(order = 1)
        private final String key2;
        private final String value;

        TestMultiKeyEnum(int key1, String key2, String value) {
            this.key1 = key1;
            this.key2 = key2;
            this.value = value;
        }

        public int getKey1() {
            return key1;
        }

        public String getKey2() {
            return key2;
        }

        public String getValue() {
            return value;
        }
    }

}
