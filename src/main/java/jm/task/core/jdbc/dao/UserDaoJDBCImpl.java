package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private String SQL_CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT PRIMARY KEY, 
            name VARCHAR(50) NOT NULL,lastName  VARCHAR(50) NOT NULL, age INT)""";


    private String SQL_INSERT_USER = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";

    private String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";

    private String SQL_SELECT = "SELECT * FROM users";

    private String SQL_CLEAN = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try (var connection = Util.getConnection(); var statement = connection.createStatement()) {
            statement.executeUpdate(SQL_CREATE_TABLE);
            System.out.println("Таблица users создана успешно");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (var connection = Util.getConnection(); var statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Таблица users успешно удалена");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (var connection = Util.getConnection(); var pstm = connection.prepareStatement(SQL_INSERT_USER)) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
            System.out.println("# User " + name + " Added into users table");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (var connection = Util.getConnection(); var pstm = connection.prepareStatement(SQL_DELETE_USER)) {
            pstm.setLong(1, id);
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("В таблице БД нет юзера с id = " + id);
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (var connection = Util.getConnection(); var pstm = connection.prepareStatement(SQL_SELECT)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setUserAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try (var connection = Util.getConnection(); var pstm = connection.prepareStatement(SQL_CLEAN)) {
            pstm.executeUpdate();
            System.out.println("Таблица users успешно очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
