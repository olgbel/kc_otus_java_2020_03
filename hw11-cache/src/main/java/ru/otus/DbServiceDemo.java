package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.model.User;
import ru.otus.core.service.CachedDbServiceUserImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createUserTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        var entityClassMetaData = new EntityClassMetaDataImpl<>(User.class);
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        JdbcMapperImpl<User> userJdbcMapper = new JdbcMapperImpl<>(dbExecutor, sessionManager, entityClassMetaData, entitySQLMetaData);
        var userDao = new UserDaoJdbc(userJdbcMapper);

        testUser(userDao);
        testUserWithCache(userDao);
    }

    private static void testUser(UserDaoJdbc userDao) {
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

    private static void testUserWithCache(UserDaoJdbc userDao) {
        MyCache<String, User> myCache = new MyCache<>();
        var dbServiceUser = new CachedDbServiceUserImpl(userDao, myCache);
        var id = dbServiceUser.saveUser(new User(3, "dbServiceUserCACHED"));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        id = dbServiceUser.saveUser(new User(3, "dbServiceUserCACHED_UPDATED"));
        user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("updated user, name:{}", crUser.getName()),
                () -> logger.info("user was not update")
        );
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
    }
}
