package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Andrey", "Troyan", (byte) 35);
        userService.saveUser("Jenya", "Reshetnikov", (byte) 25);
        userService.saveUser("Daniil", "Ivanov", (byte) 27);
        userService.saveUser("Katya", "Romanova", (byte) 28);

        System.out.println(userService.getAllUsers());

        userService.removeUserById(3);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        System.out.println(userService.getAllUsers());
    }
}
