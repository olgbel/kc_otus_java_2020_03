package ru.otus.jdbc.mapper;

import ru.otus.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final Set<Field> allFields = new HashSet<>();
    private final List<Field> fieldsWithoutId = new ArrayList<>();
    private Field fieldId;
    private Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        fillCache();
    }

    private void fillCache() {
        fillConstructor();
        fillAllFields();
        fillFieldsWithoutId();
        fillFieldId();
    }

    private void fillConstructor() {
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            System.out.println("Class " + clazz + " does not have constructor.");
        }
    }

    private void fillAllFields(){
        allFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    }

    private void fillFieldsWithoutId() {
        fieldsWithoutId.addAll(allFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList()));
    }

    private void fillFieldId() {
        fieldId = allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny()
                .orElse(null);
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return fieldId;
    }

    @Override
    public List<Field> getAllFields() {
        return new ArrayList<>(allFields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
