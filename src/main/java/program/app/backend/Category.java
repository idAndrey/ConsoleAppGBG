package program.app.backend;

public class Category {
    public enum Type { INCOME, EXPENSE }

    private Long id;
    private String name;
    private Type type;

    public Category(Long id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Type getType() { return type; }
}
