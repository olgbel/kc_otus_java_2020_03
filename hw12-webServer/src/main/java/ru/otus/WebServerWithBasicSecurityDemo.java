package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.cache.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.CachedDbServiceUserImpl;
import ru.otus.core.service.DBServiceUser;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerWithBasicSecurity;
import ru.otus.services.InMemoryLoginServiceImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

import java.util.Optional;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        MyCache<String, User> myCache = new MyCache<>();

        DBServiceUser dbServiceUser = new CachedDbServiceUserImpl(userDao, myCache);

        dbServiceUser.saveUser(new User(1, "Harry Potter", "user1", "11111"));
        dbServiceUser.saveUser(new User(2, "Hermione Granger", "user2", "11111"));
        dbServiceUser.saveUser(new User(3, "Severus Snape", "user3", "11111"));
        dbServiceUser.saveUser(new User(4, "Albus Percival Wulfric Brian Dumbledore", "user4", "11111"));
        dbServiceUser.saveUser(new User(5, "Ron Weasley", "user5", "11111"));
        dbServiceUser.saveUser(new User(6, "Lord Voldemort", "user6", "11111"));
        dbServiceUser.saveUser(new User(7, "Draco Malfoy", "user7", "11111"));

        Optional<User> user = dbServiceUser.getUser(1);
        user.ifPresent(value -> System.out.println("user: " + value));

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        LoginService loginService = new InMemoryLoginServiceImpl(dbServiceUser);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, dbServiceUser, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
