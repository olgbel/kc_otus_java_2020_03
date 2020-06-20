package ru.otus.jdbc.dao;

import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionManager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private final JdbcMapper<Account> jdbcMapper;

    public AccountDaoJdbc(JdbcMapper<Account> jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<Account> findById(long id) {
        return jdbcMapper.findById(id, Account.class);
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
