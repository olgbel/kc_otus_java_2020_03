package ru.otus.configuration;

import ru.otus.MyGson;

import java.lang.reflect.Field;

import static ru.otus.MyGsonCharacters.*;
import static ru.otus.MyGsonUtils.removeLastComma;

public class MyGsonForObjectConfiguration implements MyGsonConfiguration {
    @Override
    public String toJson(Object object) {
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
                    .append(MyGson.toJson(fieldValue))
                    .append(COMMA);
        }
        removeLastComma(builder);
        builder.append(CLOSING_BRACE);

        return builder.toString();
    }
}
