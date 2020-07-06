package ru.otus.services;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.util.security.Password;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.Optional;

public class InMemoryLoginServiceImpl extends AbstractLoginService {
    private final DBServiceUser dbServiceUser;

    public InMemoryLoginServiceImpl(DBServiceUser userDao) {
        this.dbServiceUser = userDao;
    }

    @Override
    protected String[] loadRoleInfo(UserPrincipal userPrincipal) {
        return new String[] {"user"};
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        Optional<User> dbUser = dbServiceUser.getUserByLogin(login);
        return dbUser.map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
    }
}
