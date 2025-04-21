package program.app.consoleapp;

import java.sql.DriverManager;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

import java.util.Scanner;
import program.model.User;
import program.service.UserService;


public class ConsoleAppGrain {

    public static void startApp() {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "password")) {
            UserService userService = new UserService(connection);

            MainMenu mainMenu = new MainMenu(scanner, userService);
            mainMenu.showMenu();


            // Поиск пользователей
            List<User> users = userService.searchUsersByName("test");
            System.out.println("Found users: " + users.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}