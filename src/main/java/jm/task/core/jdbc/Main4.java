package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main4 {

    public static void main(String[] args) {

//        UserService userService = new UserServiceImpl();
//        userService.dropUsersTable();
//        userService.createUsersTable();

        Session session;
        try (SessionFactory factory = new Configuration()  // SessionFactory - всегда надо закрывать!
                .configure("hibernate.cfg.xml")  // resources/hibernate.cfg.xml
                .addAnnotatedClass(User.class)
                .buildSessionFactory()) {

            session = factory.getCurrentSession();

            User user = new User("Alexey", "Parhomenko", (byte) 42);

            session.beginTransaction();        // открываем транзакцию
            session.save(user);                // инсертим объект в базу
            session.getTransaction().commit(); // закрываем транзакцию

            System.out.println(user);


        }

    }
}
