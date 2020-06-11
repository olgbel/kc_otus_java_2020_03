package ru.otus;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

import static ru.otus.MyGsonCharacters.*;

public class MyGson {
    public String toJson(Object object) {
        if (object == null) {
            return NULL;
        }

        if (object.getClass().isArray()) {
            return arrayToJson(object);
        } else if (isWrapper(object)) {
            return wrapperToJson(object);
        } else if (object instanceof Collection) {
            return collectionToJson(object);
        } else {
            return objectToJson(object);
        }
    }

    private String arrayToJson(Object object) {
        if (Array.getLength(object) == 0) {
            return EMPTY_ARRAY;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(OPENING_SQUARE_BRACKET);
        for (int i = 0; i < Array.getLength(object); i++) {
            builder.append(Array.get(object, i))
                    .append(COMMA);
        }
        removeLastComma(builder);
        builder.append(CLOSING_SQUARE_BRACKET);

        return builder.toString();
    }

    private String wrapperToJson(Object object) {
        StringBuilder builder = new StringBuilder();
        if (object instanceof String || object instanceof Character) {
            builder.append(DOUBLE_QUOTES)
                    .append(object)
                    .append(DOUBLE_QUOTES);
        } else {
            builder.append(object);
        }

        return builder.toString();
    }

    private String collectionToJson(Object object) {
        Collection collection = (Collection) object;
        if (collection.isEmpty()) {
            return EMPTY_ARRAY;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(OPENING_SQUARE_BRACKET);
        for (Object o : collection) {
            builder.append(toJson(o))
                    .append(COMMA);
        }
        removeLastComma(builder);
        builder.append(CLOSING_SQUARE_BRACKET);

        return builder.toString();
    }

    private String objectToJson(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        if (fields.length == 0) {
            return EMPTY_OBJECT;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(OPENING_BRACE);
        for (var field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                System.out.println("Cannot get value for field: " + field);
                e.printStackTrace();
            }
            builder.append(DOUBLE_QUOTES)
                    .append(field.getName())
                    .append(DOUBLE_QUOTES)
                    .append(COLON)
                    .append(toJson(fieldValue))
                    .append(COMMA);
        }
        removeLastComma(builder);
        builder.append(CLOSING_BRACE);

        return builder.toString();
    }

    private void removeLastComma(StringBuilder builder) {
        builder.deleteCharAt(builder.length() - 1);
    }

    private boolean isWrapper(Object object) {
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
