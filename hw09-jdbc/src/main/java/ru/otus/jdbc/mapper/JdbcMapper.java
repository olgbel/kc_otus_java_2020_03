package ru.otus.jdbc.mapper;

import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public interface JdbcMapper<T> {
    void insert(T objectData);

    void update(T objectData);

    void insertOrUpdate(T objectData);

    Optional<T> findById(long id, Class<T> clazz);

    SessionManagerJdbc getSessionManager();
}
