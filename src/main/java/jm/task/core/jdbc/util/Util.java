package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/myfirstbd";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Kata263!";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Connected to the database!");
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Util() {
    }
    // реализуйте настройку соеденения с БД
}
