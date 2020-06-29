package ru.otus.jdbc.dao;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.AbstractHibernateTest;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoHibernateTest extends AbstractHibernateTest {
    private SessionManagerHibernate sessionManagerHibernate;
    private UserDaoHibernate userDaoHibernate;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @Test
    void shouldFindCorrectUserById() {
        User expectedUser = new User(0, "Вася");
        saveUser(expectedUser);

        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
        mayBeUser.ifPresent(u -> {
            Hibernate.initialize(u.getAddress());
            Hibernate.initialize(u.getPhones());
        });
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void shouldFindCorrectUserWithAddressById() {
        Address address = new Address("Петроградская");
        User expectedUser = new User(0, "Вася");
        expectedUser.setAddress(address);
        saveUser(expectedUser);

        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
        mayBeUser.ifPresent(u -> {
            Hibernate.initialize(u.getAddress());
            Hibernate.initialize(u.getPhones());
        });
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void shouldFindCorrectUserWithPhonesById() {
        User expectedUser = new User(0, "Вася");
        Set<Phone> phones = new HashSet<>(Arrays.asList(new Phone(expectedUser, "7894556"), new Phone(expectedUser, "2134545")));
        expectedUser.setPhones(phones);
        saveUser(expectedUser);

        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
        mayBeUser.ifPresent(u -> {
            Hibernate.initialize(u.getAddress());
            Hibernate.initialize(u.getPhones());
        });
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void shouldCorrectSaveUser() {
        User expectedUser = new User(0, "Вася");
        expectedUser.setAddress(new Address("Горьковская"));
        Set<Phone> phones = new HashSet<>(Arrays.asList(new Phone(expectedUser, "1111111"), new Phone(expectedUser, "2222222")));
        expectedUser.setPhones(phones);

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(expectedUser);
        long id = expectedUser.getId();
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> actualUser = userDaoHibernate.findById(id);
        actualUser.ifPresent(u -> {
            Hibernate.initialize(u.getAddress());
            Hibernate.initialize(u.getPhones());
        });
        sessionManagerHibernate.commitSession();

        assertThat(actualUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);

        expectedUser = new User(id, "Не Вася");
        expectedUser.setAddress(new Address("Не Горьковская"));
        expectedUser.setPhones(new HashSet<>(Collections.singletonList(new Phone(expectedUser, "3333333"))));

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(expectedUser);
        long newId = expectedUser.getId();
        sessionManagerHibernate.commitSession();

        assertThat(newId).isGreaterThan(0).isEqualTo(id);

        sessionManagerHibernate.beginSession();
        actualUser = userDaoHibernate.findById(newId);
        actualUser.ifPresent(u -> {
            Hibernate.initialize(u.getAddress());
            Hibernate.initialize(u.getPhones());
        });
        sessionManagerHibernate.commitSession();

        assertThat(actualUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void getSessionManager() {
        assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
    }
}
