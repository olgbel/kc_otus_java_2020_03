package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.DaoException;
import ru.otus.jdbc.DbExecutor;
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

    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final EntitySQLMetaData entitySQLMetaData;

    public JdbcMapperImpl(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManager, EntityClassMetaData<T> entityClassMetaData, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.entityClassMetaData = entityClassMetaData;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public void insert(T objectData) {
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
        Optional<T> object = findById(id, (Class<T>) objectData.getClass());
        if (object.isPresent()) {
            update(objectData);
        } else {
            insert(objectData);
        }
    }

    @Override
    public Optional<T> findById(long id, Class<T> clazz) {
        String query = entitySQLMetaData.getSelectByIdSql();
        try {
            return dbExecutor.executeSelect(getConnection(), query,
                    id, rs -> {
                        try {
                            if (rs.next()) {
                                return createNewObject(rs);
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private T createNewObject(ResultSet resultSet) {
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
        Field field;
        try {
            field = entityClassMetaData.getIdField();
            field.setAccessible(true);
            return (long) field.get(objectData);
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
