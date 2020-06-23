package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;
    private String selectByIdSql;
    private String insertSql;
    private String updateSql;
    private String selectAllSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        fillQueries();
    }

    private void fillQueries() {
        fillSelectAllSql();
        fillSelectByIdSql();
        fillInsertSql();
        fillUpdateSql();
    }

    private void fillSelectAllSql() {
        selectAllSql = "select * from " + entityClassMetaData.getName();
    }

    private void fillSelectByIdSql() {
        StringBuilder builder = new StringBuilder();
        List<Field> allFields = entityClassMetaData.getAllFields();
        builder.append("select ")
                .append(allFields.stream().map(Field::getName).sorted().collect(Collectors.joining(", ")))
                .append(" from ")
                .append(entityClassMetaData.getName())
                .append(" where ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?");
        selectByIdSql = builder.toString();
    }

    private void fillInsertSql() {
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
        insertSql = builder.toString();
    }

    private void fillUpdateSql() {
        StringBuilder builder = new StringBuilder();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        builder.append("update ")
                .append(entityClassMetaData.getName())
                .append(" set ")
                .append(fieldsWithoutId.stream().map(field -> field.getName() + " = ?").sorted().collect(Collectors.joining(",")))
                .append(" where ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?");
        updateSql = builder.toString();
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}
