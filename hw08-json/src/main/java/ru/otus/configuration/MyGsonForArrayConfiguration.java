package ru.otus.configuration;

import java.lang.reflect.Array;

import static ru.otus.MyGsonCharacters.*;
import static ru.otus.MyGsonCharacters.CLOSING_SQUARE_BRACKET;
import static ru.otus.MyGsonUtils.removeLastComma;

public class MyGsonForArrayConfiguration implements MyGsonConfiguration {
    @Override
    public String toJson(Object object) {
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
}
