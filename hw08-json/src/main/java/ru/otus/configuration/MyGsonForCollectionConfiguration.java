package ru.otus.configuration;

import ru.otus.MyGson;

import java.util.Collection;

import static ru.otus.MyGsonCharacters.*;
import static ru.otus.MyGsonCharacters.CLOSING_SQUARE_BRACKET;
import static ru.otus.MyGsonUtils.removeLastComma;

public class MyGsonForCollectionConfiguration implements MyGsonConfiguration {
    @Override
    public String toJson(Object object) {
        Collection collection = (Collection) object;
        if (collection.isEmpty()) {
            return EMPTY_ARRAY;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(OPENING_SQUARE_BRACKET);
        for (Object o : collection) {
            builder.append(MyGson.toJson(o))
                    .append(COMMA);
        }
        removeLastComma(builder);
        builder.append(CLOSING_SQUARE_BRACKET);

        return builder.toString();
    }
}
