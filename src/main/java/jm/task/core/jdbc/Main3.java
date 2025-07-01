package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

// https://youtu.be/1yvajv4ywk0?si=xnRL_vTqky4BUvxJ&t=728

public class Main3 {

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

            session.beginTransaction();        // открываем транзакцию

            List<User> usersList = session.createQuery("from User where userAge > 18 AND name LIKE 'n%' ", User.class).getResultList();  // HQL-код

            usersList.forEach(System.out::println);

            session.getTransaction().commit();

        }
    }
}
