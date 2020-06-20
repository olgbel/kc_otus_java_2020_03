package ru.otus;

import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createUserTable(dataSource);
        demo.createAccountTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);

        testUser(sessionManager);
        testAccount(sessionManager);
    }

    private static void testUser(SessionManagerJdbc sessionManager) {
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        var entityClassMetaData = new EntityClassMetaDataImpl<>(User.class);
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        JdbcMapperImpl<User> userJdbcMapper = new JdbcMapperImpl<>(dbExecutor, sessionManager, entityClassMetaData, entitySQLMetaData);
        var userDao = new UserDaoJdbc(userJdbcMapper);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(1, "dbServiceUser"));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        id = dbServiceUser.saveUser(new User(1, "dbServiceUserUPDATED"));
        user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("updated user, name:{}", crUser.getName()),
                () -> logger.info("user was not update")
        );
    }

    private static void testAccount(SessionManagerJdbc sessionManager) {
        DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();
        var entityClassMetaData = new EntityClassMetaDataImpl<>(Account.class);
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        JdbcMapperImpl<Account> accountJdbcMapper = new JdbcMapperImpl<>(dbExecutor, sessionManager, entityClassMetaData, entitySQLMetaData);

        var accountDao = new AccountDaoJdbc(accountJdbcMapper);

        var dbServiceAccount = new DbServiceAccountImpl(accountDao);
        var id = dbServiceAccount.saveAccount(new Account(1, "credit", 12));
        Optional<Account> account = dbServiceAccount.getAccount(id);

        account.ifPresentOrElse(
                crAccount -> logger.info("created account, type: {}; rest: {}", crAccount.getType(), crAccount.getRest()),
                () -> logger.info("account was not created")
        );

        id = dbServiceAccount.saveAccount(new Account(1, "debit", 11));
        account = dbServiceAccount.getAccount(id);
        account.ifPresentOrElse(
                crAccount -> logger.info("updated account, type:{}, rest:{}", crAccount.getType(), crAccount.getRest()),
                () -> logger.info("account was not updated")
        );
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no long auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
    }
}
