package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.DaoException;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public JdbcMapperImpl(DbExecutorImpl<T> dbExecutor, SessionManagerJdbc sessionManager) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
    }

    @Override
    public void insert(T objectData) {
        var entityClassMetadata = new EntityClassMetaDataImpl<T>((Class<T>) objectData.getClass());
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetadata);

        String query = entitySQLMetaData.getInsertSql();
        List<Object> params = fillObjectParams(objectData);
        try {
            dbExecutor.executeInsert(getConnection(), query, params);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(T objectData) {
        var entityClassMetadata = new EntityClassMetaDataImpl<T>((Class<T>) objectData.getClass());
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetadata);

        String query = entitySQLMetaData.getUpdateSql();
        List<Object> params = fillObjectParams(objectData);
        long id = getIdFieldValue(objectData);
        params.add(id);
        try {
            dbExecutor.executeInsert(getConnection(), query, params);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        long id = getIdFieldValue(objectData);
        Optional<T> object = (Optional<T>) findById(id, (Class<T>) objectData.getClass());
        if (object.isPresent()) {
            update(objectData);
        } else {
            insert(objectData);
        }
    }

    @Override
    public T findById(long id, Class<T> clazz) {
        var entityClassMetadata = new EntityClassMetaDataImpl<T>(clazz);
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetadata);

        String query = entitySQLMetaData.getSelectByIdSql();
        try {
            return (T) dbExecutor.executeSelect(getConnection(), query,
                    id, rs -> {
                        try {
                            if (rs.next()) {
                                return createNewObject(rs, clazz);
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private T createNewObject(ResultSet resultSet, Class<T> clazz) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        T newObject = null;
        try {
            newObject = constructor.newInstance();
            List<Field> fields = entityClassMetaData.getAllFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (resultSet.getObject(field.getName()).getClass().equals(BigDecimal.class)) {
                    field.set(newObject, ((Number) resultSet.getObject(field.getName())).intValue());
                } else {
                    field.set(newObject, resultSet.getObject(field.getName()));
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            e.printStackTrace();
        }
        return newObject;
    }

    private List<Object> fillObjectParams(T objectData) {
        List<Object> params = new ArrayList<>();
        var entityClassMetaData = new EntityClassMetaDataImpl(objectData.getClass());

        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(objectData);
            } catch (IllegalAccessException e) {
                logger.error("no privileges");
                e.printStackTrace();
            }
            params.add(value);
        }
        return params;
    }

    private long getIdFieldValue(T objectData) {
        var entityClassMetadata = new EntityClassMetaDataImpl<T>((Class<T>) objectData.getClass());
        Field field;
        try {
            field = objectData.getClass().getDeclaredField(entityClassMetadata.getIdField().getName());
            field.setAccessible(true);
            return (long) field.get(objectData);
        } catch (NoSuchFieldException e) {
            logger.error("No id field in the class: {}", objectData.getClass());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("no privileges");
            e.printStackTrace();
        }
        return -1;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    public SessionManagerJdbc getSessionManager() {
        return sessionManager;
    }
}
