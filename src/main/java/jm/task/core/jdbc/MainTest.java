package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class MainTest {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();

        userService.saveUser("Andrey", "Troyan", (byte) 35);
        userService.saveUser("Andrey", "Fomin", (byte) 30);
        userService.saveUser("Alexey", "Parhomenko", (byte) 42);

        System.out.println(userService.getAllUsers());

        userService.removeUserById(2);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        System.out.println(userService.getAllUsers());
    }
}
