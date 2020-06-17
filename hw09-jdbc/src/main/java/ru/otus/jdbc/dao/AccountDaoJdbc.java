package ru.otus.jdbc.dao;

import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionManager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {

    private final JdbcMapperImpl jdbcMapper;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<Account> dbExecutor) {
        jdbcMapper = new JdbcMapperImpl(dbExecutor, sessionManager);
    }

    @Override
    public Optional<Account> findById(long id) {
        return (Optional<Account>) jdbcMapper.findById(id, Account.class);
    }

    @Override
    public long insertAccount(Account account) {
        jdbcMapper.insert(account);
        return account.getNo();
    }

    @Override
    public void updateAccount(Account account) {
        jdbcMapper.update(account);
    }

    @Override
    public void insertOrUpdate(Account account) {
        jdbcMapper.insertOrUpdate(account);
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
