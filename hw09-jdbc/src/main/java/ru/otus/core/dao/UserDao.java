package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionManager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
