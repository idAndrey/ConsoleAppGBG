package program.app.consoleapp;

import program.model.User;
import program.service.UserService;

import java.util.Optional;
import java.util.Scanner;

public class UserSessionMenu {

    private Scanner scanner;
    private UserService userService;
    private User user;

    UserSessionMenu(Scanner scanner, UserService userService, Optional<User> authUser) {
        this.scanner = scanner;
        this.userService = userService;
        authUser.ifPresent(value -> this.user = value);
    }

    public void showMenu() {
        while (true) {
            System.out.println("Мониторинг и отчёты:");
            System.out.println("1. Счета");
            System.out.println("2. Категории");
            System.out.println("3. Транзакции");
            System.out.println("4. Выйти из аккаунта");

            try {
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> accountController();
                    case "2" -> categoryController();
//                    case "3" -> transactionController.start();
                    case "3" -> transactionController();
                    case "4" -> {
                                    logoff();
                                    return;
                                }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка (manageUserSession): " + e.getMessage());
            }
        }
    }

    private void logoff() {
        System.out.println("Выход из аккаунта...\n");
//        System.out.println("Работа завершена.");
    }

    private void accountController() {
        System.out.println("Заглушка метода accountController()...\n");
    }

    private void categoryController() {
        System.out.println("Заглушка метода categoryController()...\n");
    }

    private void transactionController() {
        System.out.println("Заглушка метода transactionController()...\n");
    }
}
