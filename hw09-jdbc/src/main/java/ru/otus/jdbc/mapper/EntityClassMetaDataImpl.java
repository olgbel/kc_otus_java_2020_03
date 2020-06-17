package ru.otus.jdbc.mapper;

import ru.otus.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    Class<T> clazz;
    Map<Class<T>, Field[]> fieldsCache = new HashMap<>();

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            System.out.println("Class " + clazz + " does not have constructor.");
        }
        return null;
    }

    @Override
    public Field getIdField() {
        fieldsCache.putIfAbsent(clazz, clazz.getDeclaredFields());
        return Stream.of(fieldsCache.get(clazz))
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Field> getAllFields() {
        fieldsCache.putIfAbsent(clazz, clazz.getDeclaredFields());
        return List.of(fieldsCache.get(clazz));
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        fieldsCache.putIfAbsent(clazz, clazz.getDeclaredFields());
        return Stream.of(fieldsCache.get(clazz))
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }
}
