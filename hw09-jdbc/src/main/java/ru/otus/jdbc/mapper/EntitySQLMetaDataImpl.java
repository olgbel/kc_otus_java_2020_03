package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;
    private String SELECT_BY_ID_SQL;
    private String INSERT_SQL;
    private String UPDATE_SQL;
    private String SELECT_ALL_SQL;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        if (SELECT_ALL_SQL == null) {
            SELECT_ALL_SQL = "select * from " +
                    entityClassMetaData.getName();
        }
        return SELECT_ALL_SQL;
    }

    @Override
    public String getSelectByIdSql() {
        if (SELECT_BY_ID_SQL == null) {
            StringBuilder builder = new StringBuilder();
            List<Field> allFields = entityClassMetaData.getAllFields();
            builder.append("select ")
                    .append(allFields.stream().map(Field::getName).sorted().collect(Collectors.joining(", ")))
                    .append(" from ")
                    .append(entityClassMetaData.getName())
                    .append(" where ")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(" = ?");
            SELECT_BY_ID_SQL = builder.toString();
        }
        return SELECT_BY_ID_SQL;
    }

    @Override
    public String getInsertSql() {
        if (INSERT_SQL == null) {
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
            INSERT_SQL = builder.toString();
        }
        return INSERT_SQL;
    }

    @Override
    public String getUpdateSql() {
        if (UPDATE_SQL == null) {
            StringBuilder builder = new StringBuilder();
            List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
            builder.append("update ")
                    .append(entityClassMetaData.getName())
                    .append(" set ")
                    .append(fieldsWithoutId.stream().map(field -> field.getName() + " = ?").sorted().collect(Collectors.joining(",")))
                    .append(" where ")
                    .append(entityClassMetaData.getIdField().getName())
                    .append(" = ?");
            UPDATE_SQL = builder.toString();
        }
        return UPDATE_SQL;
    }
}
