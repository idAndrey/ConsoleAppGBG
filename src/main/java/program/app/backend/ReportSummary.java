package program.app.backend;

import java.time.LocalDate;
import java.util.Map;

public class ReportSummary {
    private Long userId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double totalIncome;
    private double totalExpense;
    private double balance;
    private Map<String, Double> categorySummary;

    public ReportSummary(Long userId, LocalDate fromDate, LocalDate toDate, double totalIncome, double totalExpense, double balance, Map<String, Double> categorySummary) {
        this.userId = userId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.categorySummary = categorySummary;
    }

    public String toString() {
        return "Отчёт по пользователю ID: " + userId +
                "\nПериод: " + fromDate + " - " + toDate +
                "\nДоходы: " + totalIncome +
                "\nРасходы: " + totalExpense +
                "\nБаланс: " + balance +
                "\nКатегории: " + categorySummary;
    }
}
