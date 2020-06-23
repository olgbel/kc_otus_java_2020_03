package ru.otus;

import ru.otus.configuration.*;

import java.util.Collection;

import static ru.otus.MyGsonCharacters.*;
import static ru.otus.MyGsonUtils.isWrapper;

public class MyGson {
    public static String toJson(Object object) {
        if (object == null) {
            return NULL;
        }

        if (object.getClass().isArray()) {
            return new MyGsonForArrayConfiguration().toJson(object);
        } else if (isWrapper(object)) {
            return new MyGsonForWrapperConfiguration().toJson(object);
        } else if (object instanceof Collection) {
            return new MyGsonForCollectionConfiguration().toJson(object);
        } else {
            return new MyGsonForObjectConfiguration().toJson(object);
        }
    }
}
