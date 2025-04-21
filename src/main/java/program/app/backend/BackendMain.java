package program.app.backend;

import java.time.LocalDate;

public class BackendMain {

//    private final static String HOST = "localhost"; // сервер базы данных
//    private final static String PORT = "5432"; // порт подключения
//    private final static String DATABASENAME = "GrainByGrain"; // имя базы
//    private final static String USERNAME = "postgres"; // учетная запись пользователя
//    private final static String PASSWORD = "password";
//    private static Connection connection;// пароль

    public static void startApp() {
//    public static void main(String[] args) {

        TransactionService service = new TransactionService();

        // Базовый вывод
        double income = service.getTotalIncome(1L);
        double expense = service.getTotalExpense(1L);
        double balance = income - expense;

        System.out.println("Доходы: " + income);
        System.out.println("Расходы: " + expense);
        System.out.println("Баланс: " + balance);


        LocalDate start = LocalDate.of(2025, 4, 1);
        LocalDate end = LocalDate.of(2025, 4, 30);

        ReportSummary report = service.generateReport(1L, start, end);

        System.out.println("\n Отчёт за период " + start + " — " + end + ":");
        System.out.println(report);
    }
}