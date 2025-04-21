package program.app.backend;

import java.time.LocalDate;

public class Transaction {
    public enum Status { NEW, CONFIRMED, PROCESSED, DELETED }

    private Long id;
    private Long userId;
    private Long categoryId;
    private double amount;
    private String description;
    private LocalDate transactionDate;
    private Status status;

    public Transaction(Long id, Long userId, Long categoryId, double amount, String description, LocalDate transactionDate, Status status) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getCategoryId() { return categoryId; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public Status getStatus() { return status; }
}

