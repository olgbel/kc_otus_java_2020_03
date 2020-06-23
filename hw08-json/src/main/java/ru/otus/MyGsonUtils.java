package ru.otus;

public class MyGsonUtils {
    public static void removeLastComma(StringBuilder builder) {
        builder.deleteCharAt(builder.length() - 1);
    }

    public static boolean isWrapper(Object object) {
        return object instanceof Character ||
                object instanceof Byte ||
                object instanceof Short ||
                object instanceof Integer ||
                object instanceof Float ||
                object instanceof Double ||
                object instanceof Boolean ||
                object instanceof Long ||
                object instanceof String;
    }
}
