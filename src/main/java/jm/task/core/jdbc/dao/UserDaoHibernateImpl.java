package jm.task.core.jdbc.dao;

// https://youtu.be/9pYOYJOIUyY?si=s3rlW2v5a_OBSXKw&t=194

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (var connection = Util.getConnection(); var statement = connection.createStatement()) {

            statement.executeUpdate("""
            CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT PRIMARY KEY, 
            name VARCHAR(50) NOT NULL,lastName  VARCHAR(50) NOT NULL, age INT)""");

            System.out.println("Таблица users создана успешно");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (var connection = Util.getConnection(); var statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица users успешно удалена");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session;
        try (SessionFactory factory = new Configuration()  // Создаем 1 раз и переиспользуем, SessionFactory - всегда надо закрывать!
                .configure("hibernate.cfg.xml")  // resources/hibernate.cfg.xml
                .addAnnotatedClass(User.class)
                .buildSessionFactory()) {

            User user = new User(name, lastName, age);

            session = factory.getCurrentSession();

            session.beginTransaction();                // открываем транзакцию
            session.save(user);                        // инсертим объект в базу
            session.getTransaction().commit();         // закрываем транзакцию
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session;
        try (SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory()) {

            session = factory.getCurrentSession();

            session.beginTransaction();  // открываем транзакцию

            User user =  session.get(User.class, id);

            session.delete(user);

            session.getTransaction().commit(); // закрываем транзакцию
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> usersList;

        Session session;
        try (SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory()) {

            session = factory.getCurrentSession();

            session.beginTransaction();  // открываем транзакцию

            usersList = session.createQuery("from User").getResultList(); // выполняем HQL-код

            session.getTransaction().commit(); // закрываем транзакцию
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Session session;
        try (SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory()) {

            session = factory.getCurrentSession();

            session.beginTransaction();  // открываем транзакцию

            session.createQuery("delete User").executeUpdate();

            session.getTransaction().commit(); // закрываем транзакцию
        }
    }
}
