package jm.task.core.jdbc.dao;

// https://youtu.be/9pYOYJOIUyY?si=s3rlW2v5a_OBSXKw&t=194

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();                    // открываем транзакцию

            session.createNativeQuery("""
                    CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT PRIMARY KEY, 
                    name VARCHAR(50) NOT NULL,lastName  VARCHAR(50) NOT NULL, age INT)""").executeUpdate();

            session.getTransaction().commit();             // закрываем транзакцию

            System.out.println("Таблица users создана успешно");

        } catch (HibernateException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

            session.getTransaction().commit();

            System.out.println("Таблица users успешно удалена");

        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();                    // открываем транзакцию

            session.save(new User(name, lastName, age));   // инсертим объект в базу

            session.getTransaction().commit();             // закрываем транзакцию

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();  // открываем транзакцию

//            User user = session.get(User.class, id);
//
//            session.delete(user);

            session.createQuery("delete User where id = :userId").setParameter("userId", id).executeUpdate();

            session.getTransaction().commit(); // закрываем транзакцию

            System.out.println("User удален");

        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> usersList;

        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();  // открываем транзакцию

            usersList = session.createQuery("from User").getResultList(); // выполняем HQL-код

            session.getTransaction().commit(); // закрываем транзакцию

        } catch (HibernateException e) {

            throw new RuntimeException(e);
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();  // открываем транзакцию

            session.createQuery("delete User").executeUpdate();

            session.getTransaction().commit(); // закрываем транзакцию

            System.out.println("Таблица очищена");

        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }
}
