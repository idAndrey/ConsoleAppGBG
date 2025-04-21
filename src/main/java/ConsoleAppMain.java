import program.app.backend.BackendMain;
import program.app.consoleapp.ConsoleAppGrain;
import program.model.User;

import java.util.Scanner;

public class ConsoleAppMain {

    private static BackendMain backendMain = new BackendMain();
    private static ConsoleAppGrain consoleApp = new ConsoleAppGrain();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите приложение:");
            System.out.println("1. Backend");
            System.out.println("2. Console App");
            System.out.println("3. Завершение работы");

            try {

                String choice = scanner.nextLine();

                switch (choice) {

                    case "1" -> {

                        backendMain = new BackendMain();
                        backendMain.startApp();

                    }
                    case "2" -> {

                        consoleApp = new ConsoleAppGrain();
                        consoleApp.startApp();

                    }
                    case "3" -> {

                        System.out.println("Работа завершена.");
                        return;

                    }

                    default -> System.out.println("Неверная команда.");
                }

            } catch (Exception e) {
                System.out.println("Произошла ошибка (main): " + e.getMessage());
            }
        }


    }
}
