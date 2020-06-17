package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    EntityClassMetaDataImpl entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaDataImpl entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " +
                entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder builder = new StringBuilder();
        List<Field> allFields = entityClassMetaData.getAllFields();
        builder.append("select ")
                .append(allFields.stream().map(Field::getName).sorted().collect(Collectors.joining(", ")))
                .append(" from ")
                .append(entityClassMetaData.getName())
                .append(" where ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?");
        return builder.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder builder = new StringBuilder();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        builder.append("insert into ")
                .append(entityClassMetaData.getName())
                .append("(")
                .append(fieldsWithoutId.stream().map(Field::getName).sorted().collect(Collectors.joining(", ")))
                .append(") values (")
                .append("?,".repeat(Math.max(1, fieldsWithoutId.size())))
                .deleteCharAt(builder.length() - 1)
                .append(")");

        return builder.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder builder = new StringBuilder();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        builder.append("update ")
                .append(entityClassMetaData.getName())
                .append(" set ")
                .append(fieldsWithoutId.stream().map(field -> field.getName() + " = ?").sorted().collect(Collectors.joining(",")))
                .append(" where ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?");

        return builder.toString();
    }
}
