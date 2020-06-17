package ru.otus.jdbc.dao;

import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionManager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private final JdbcMapperImpl jdbcMapper;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<User> dbExecutor) {
        jdbcMapper = new JdbcMapperImpl(dbExecutor, sessionManager);
    }

    @Override
    public Optional<User> findById(long id) {
        return (Optional<User>) jdbcMapper.findById(id, User.class);
    }

    @Override
    public long insertUser(User user) {
        jdbcMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(User user) {
        jdbcMapper.update(user);
    }

    @Override
    public void insertOrUpdate(User user) {
        jdbcMapper.insertOrUpdate(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
