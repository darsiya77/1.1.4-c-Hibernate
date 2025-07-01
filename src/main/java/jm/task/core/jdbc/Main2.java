package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main2 {

    public static void main(String[] args) {

//        UserService userService = new UserServiceImpl();
//        userService.dropUsersTable();
//        userService.createUsersTable();
//        userService.saveUser("Andrey", "Troyan", (byte) 35);
//        userService.saveUser("Jenya", "Reshetnikov", (byte) 25);
//        userService.saveUser("Daniil", "Ivanov", (byte) 27);
//        userService.saveUser("Katya", "Romanova", (byte) 28);
//
//        System.out.println(userService.getAllUsers());
//
//        userService.removeUserById(3);
//
//        System.out.println(userService.getAllUsers());
//
//        userService.cleanUsersTable();
//
//        System.out.println(userService.getAllUsers());


        Session session;
        try (SessionFactory factory = new Configuration()  // SessionFactory - всегда надо закрывать!
                .configure("hibernate.cfg.xml")  // resources/hibernate.cfg.xml
                .addAnnotatedClass(User.class)
                .buildSessionFactory()) {

            session = factory.getCurrentSession();

            User user = new User("AAAA", "SSSS", (byte) 48);

            session.beginTransaction();        // открываем транзакцию
            session.save(user);                // инсертим объект ы базу
//            session.getTransaction().commit(); // закрываем транзакцию

//            long myId = user.getId();
            User us =  session.get(User.class, user.getId());
//            List<User> users = session.createQuery("from users").list();
            session.getTransaction().commit();
            System.out.println(us);

            

        }
    }
}
