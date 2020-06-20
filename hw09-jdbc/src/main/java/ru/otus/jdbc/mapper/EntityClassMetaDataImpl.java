package ru.otus.jdbc.mapper;

import ru.otus.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final Set<Field> fieldsCache = new HashSet<>();
    private Field fieldId = null;
    private List<Field> fieldsWithoutId = new ArrayList<>();

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
        if (fieldsCache.isEmpty()) {
            fillFieldCache();
        }

        if (fieldId == null) {
            fieldId = fieldsCache.stream()
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findAny()
                    .orElse(null);
        }
        return fieldId;
    }

    @Override
    public List<Field> getAllFields() {
        if (fieldsCache.isEmpty()) {
            fillFieldCache();
        }
        return new ArrayList<>(fieldsCache);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsCache.isEmpty()) {
            fillFieldCache();
        }
        if (fieldsWithoutId.isEmpty()) {
            fieldsWithoutId = fieldsCache.stream()
                    .filter(field -> !field.isAnnotationPresent(Id.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList());
        }
        return fieldsWithoutId;
    }

    private void fillFieldCache() {
        fieldsCache.addAll(Arrays.asList(clazz.getDeclaredFields()));
    }
}
