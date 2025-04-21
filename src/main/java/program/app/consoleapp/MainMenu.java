package program.app.consoleapp;

import program.model.User;
import program.service.UserService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private UserService userService;
    private UserSessionMenu userSessionMenu;

    public MainMenu(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("Главное меню:");
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Завершение работы");

            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> { newUser(); }
                    case "2" -> { authenticate(); }
                    case "3" -> {
                                    logoff();
                                    return;
                                }
                    default -> System.out.println("Неверная команда.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка (mainMenu): " + e.getMessage());
            }
        }
    }

    private void newUser() {
//    private static void newUser(Scanner scanner, UserService userService, DatabaseService databaseService) {

        try {

            // Регистрация
            System.out.print("Установите логин (от 4 до 20-ти символов): ");
            String login = scanner.nextLine();

            System.out.print("Установите пароль (не менее 6-ти символов): ");
            String password = scanner.nextLine();

//            System.out.print("Введите адрес электронной почты: ");
//            String email = scanner.nextLine();

            System.out.print("Введите краткое имя: ");
            String username = scanner.nextLine();

//            System.out.print("Введите полное имя: ");
//            String fullname = scanner.nextLine();

//            User newUser = new User(login, password, email, username);

//            userService.registerUser(username, password);
//            userService.registerUser(newUser, databaseService);

            User newUser = userService.registerUser(login, password, username);
            System.out.println("Registered user: " + newUser);

        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void authenticate() {

        // Аутентификация
        Optional<User> authUser = null;
        try {

            System.out.print("Ваш логин: ");
            String login = scanner.nextLine();
            System.out.print("Ваш пароль: ");
            String password = scanner.nextLine();

            authUser = userService.authenticate(login, password);

            if (authUser.isPresent()) {

                System.out.println("Authenticated as: " + authUser.get().getUserName());
//                manageUserSession(scanner, authUser);
                userSessionMenu = new UserSessionMenu(scanner, userService, authUser);
                userSessionMenu.showMenu();

            } else {

                System.out.println("Вход не выполнен. Проверьте логин и пароль.");

            }

        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void logoff() {
        System.out.println("Работа завершена.");
    }

    private void manageUserSession(Scanner scanner, Optional<program.model.User> currentUser) {

        while (true) {
            System.out.println("Мониторинг и отчёты:");
            System.out.println("1. Счета");
            System.out.println("2. Категории");
            System.out.println("3. Транзакции");
            System.out.println("4. Возврат");

            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> accountController();
                    case "2" -> categoryController();
//                    case "3" -> transactionController.start();
                    case "3" -> transactionController();
                    case "4" -> {
                        System.out.println("Выход из аккаунта...\n");
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка (manageUserSession): " + e.getMessage());
            }
        }
    }

    private void accountController() {

    }
    private void categoryController() {

    }
    private void transactionController() {

    }

}
