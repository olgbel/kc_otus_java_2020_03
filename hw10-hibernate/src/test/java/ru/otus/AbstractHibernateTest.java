package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.hibernate.HibernateUtils;

public class AbstractHibernateTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";

    protected static final String FIELD_ID = "id";
    protected static final String FIELD_NAME = "name";
    protected static final String TEST_USER_NAME = "Вася";
    protected static final String TEST_USER_NEW_NAME = "НЕ Вася";
    protected static final String TEST_USER_NEW_NAME2 = "Совершенно точно НЕ Вася";


    protected SessionFactory sessionFactory;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Address.class, Phone.class);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    protected User buildDefaultUser() {
        return new User(0, TEST_USER_NAME);
    }

    protected void saveUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            saveUser(session, user);
        }
    }

    protected void saveUser(Session session, User user) {
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    protected User loadUser(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            System.out.println("user: " + user);
            return user;
        }
    }

    protected EntityStatistics getUserStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(User.class.getName());
    }

}
