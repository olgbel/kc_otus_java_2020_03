package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    Optional<User> loadUser(long id);

    SessionManager getSessionManager();
}
