package com.jukipuki.enums;

import com.jukipuki.enums.lookup.Indexable;
import com.jukipuki.enums.lookup.Key;
import com.jukipuki.enums.lookup.Name;

import java.util.ArrayList;
import java.util.List;

public class LookupTest {

    public static void main2(String[] args) {
        /*TestEnum testEnum = lookupEnum(TestEnum.class, 2);
        TestEnum testEnumNull = lookupEnumNullable(TestEnum.class, 5);
        TestEnum testEnumDefault = lookupEnumNullable(TestEnum.class, 5, THIRD);
        System.out.println("Enum: " + testEnum.getName() + "[" + testEnum.getKey() + "]");
        System.out.println("Null: " + testEnumNull);
        System.out.println("Default: " + testEnumDefault.getName() + "[" + testEnumDefault.getKey() + "]");
        System.out.println();
        TestMultiKeyEnum testEnumMulti = lookupEnum(TestMultiKeyEnum.class, new Integer[]{2, 2});
        TestMultiKeyEnum testEnumNullMulti = lookupEnumNullable(TestMultiKeyEnum.class, new Integer[]{5, 5});
        TestMultiKeyEnum testEnumDefaultMulti = lookupEnumNullable(TestMultiKeyEnum.class, new Integer[]{5, 5}, FIRST);
        System.out.println("Multi Enum: " + testEnumMulti.getName() + "[" + Arrays.toString(testEnumMulti.getKey()) + "]");
        System.out.println("Multi Null: " + testEnumNullMulti);
        System.out.println("Multi Default: " + testEnumDefaultMulti.getName() + "[" + Arrays.toString(testEnumDefaultMulti.getKey()) + "]");
        System.out.println();
        TestListKeyEnum testEnumList = lookupEnum(TestListKeyEnum.class, new ArrayList<Integer>() {{add(2);}});
        TestListKeyEnum testEnumNullList = lookupEnumNullable(TestListKeyEnum.class, new ArrayList<Integer>() {{add(5);}});
        TestListKeyEnum testEnumDefaultList = lookupEnumNullable(TestListKeyEnum.class, new ArrayList<Integer>() {{add(5);}}, SECOND);
        System.out.println("Multi Enum: " + testEnumList.getName() + "[" + testEnumList.getKey() + "]");
        System.out.println("Multi Null: " + testEnumNullList);
        System.out.println("Multi Default: " + testEnumDefaultList.getName() + "[" + testEnumDefaultList.getKey() + "]");
        System.out.println();*/
    }

    enum TestEnum implements Indexable<Integer> {

        FIRST(1, "First"),
        SECOND(2, "Second"),
        THIRD(3, "Third");

        @Key
        private final int key;

        @Name
        private final String value;

        TestEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getIndexKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    enum TestMultiKeyEnum implements Indexable<Integer[]> {

        FIRST(new Integer[]{1, 1}, "First"),
        SECOND(new Integer[]{2, 2}, "Second"),
        THIRD(new Integer[]{3, 3}, "Third");

        @Key
        private final Integer[] key;

        @Name
        private final String value;

        TestMultiKeyEnum(Integer[] key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum TestListKeyEnum implements Indexable<List<Integer>> {

        FIRST(new ArrayList<Integer>() {{
            add(1);
        }}, "First"),
        SECOND(new ArrayList<Integer>() {{
            add(2);
        }}, "Second"),
        THIRD(new ArrayList<Integer>() {{
            add(3);
        }}, "Third");

        @Key
        private final List<Integer> key;

        @Name
        private final String value;

        TestListKeyEnum(List<Integer> key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
