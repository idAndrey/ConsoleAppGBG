package program.app.backend;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {

    private List<Category> mockCategories = List.of(
            new Category(1L, "Salary", Category.Type.INCOME),
            new Category(2L, "Food", Category.Type.EXPENSE),
            new Category(3L, "Transport", Category.Type.EXPENSE)
    );

    private Map<Long, Category> categoryMap = mockCategories.stream()
            .collect(Collectors.toMap(Category::getId, c -> c));

    private List<Transaction> mockTransactions = List.of(
            new Transaction(1L, 1L, 1L, 60000, "Зарплата", LocalDate.of(2025, 4, 1), Transaction.Status.NEW),
            new Transaction(2L, 1L, 2L, 9000, "Продукты", LocalDate.of(2025, 4, 2), Transaction.Status.NEW),
            new Transaction(3L, 1L, 3L, 5000, "Такси", LocalDate.of(2025, 4, 5), Transaction.Status.NEW)
    );

    public double getTotalIncome(Long userId) {
        return mockTransactions.stream()
                .filter(t -> t.getUserId().equals(userId))
                .filter(t -> categoryMap.get(t.getCategoryId()).getType() == Category.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense(Long userId) {
        return mockTransactions.stream()
                .filter(t -> t.getUserId().equals(userId))
                .filter(t -> categoryMap.get(t.getCategoryId()).getType() == Category.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public ReportSummary generateReport(Long userId, LocalDate from, LocalDate to) {
        List<Transaction> filtered = mockTransactions.stream()
                .filter(t -> t.getUserId().equals(userId))
                .filter(t -> !t.getTransactionDate().isBefore(from) && !t.getTransactionDate().isAfter(to))
                .toList();

        double income = filtered.stream()
                .filter(t -> categoryMap.get(t.getCategoryId()).getType() == Category.Type.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = filtered.stream()
                .filter(t -> categoryMap.get(t.getCategoryId()).getType() == Category.Type.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<String, Double> byCategory = filtered.stream()
                .collect(Collectors.groupingBy(
                        t -> categoryMap.get(t.getCategoryId()).getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        return new ReportSummary(userId, from, to, income, expense, income - expense, byCategory);
    }
}
