package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.*;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user = new User(0, "Vasya");
        user.setAddress(new Address("Петроградская"));
        Set<Phone> phones = new HashSet<>(Arrays.asList(new Phone(user, "1111111"), new Phone(user, "2222222")));
        user.setPhones(phones);

        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.loadUser(id);

        User updatedUser = new User(1L, "А! Нет. Это же совсем не Вася");
        updatedUser.setAddress(new Address("Василеостровская"));
        phones = new HashSet<>(Collections.singletonList(new Phone(updatedUser, "3333333")));
        updatedUser.setPhones(phones);

        id = dbServiceUser.saveUser(updatedUser);
        Optional<User> mayBeUpdatedUser = dbServiceUser.loadUser(id);
        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(user -> {
            System.out.println(user);
            System.out.println("phones: " + user.getPhones());
            System.out.println("address: " + user.getAddress());
        }, () -> logger.info("User not found"));
    }
}
