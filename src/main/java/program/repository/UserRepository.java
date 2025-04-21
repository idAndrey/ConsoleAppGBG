package program.repository;

import program.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    // Создание пользователя
    public User create(User user) throws SQLException {
        String sql = "INSERT INTO users (login, password, password_hash, user_name, is_active) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING user_id";
//                "VALUES (?, ?, ?, ?, ?) RETURNING user_id, created_at, updated_at";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getUserName());
            statement.setBoolean(5, user.getIsActive());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println(resultSet.getLong("user_id"));
                    user.setUserId(resultSet.getLong("user_id"));
//                    user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
//                    user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return user;
    }

    // Поиск по ID
    public Optional<User> findById(Long userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    // Поиск по логину
    public Optional<User> findByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    // Получение всех пользователей
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        }
        return users;
    }

    // Обновление пользователя
    public User update(User user) throws SQLException {
        String sql = "UPDATE users SET login = ?, password = ?, password_hash = ?, " +
                "user_name = ?, is_active = ?, updated_at = CURRENT_TIMESTAMP " +
                "WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getUserName());
            statement.setBoolean(5, user.getIsActive());
            statement.setLong(6, user.getUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        }
        return user;
    }

    // Удаление пользователя
    public boolean delete(Long userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Вспомогательный метод для маппинга ResultSet в User
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setUserName(resultSet.getString("user_name"));
        user.setIsActive(resultSet.getBoolean("is_active"));
//        user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
//        user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return user;
    }
}