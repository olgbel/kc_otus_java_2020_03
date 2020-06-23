package ru.otus.configuration;

import static ru.otus.MyGsonCharacters.DOUBLE_QUOTES;

public class MyGsonForWrapperConfiguration implements  MyGsonConfiguration {
    @Override
    public String toJson(Object object) {
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
}
