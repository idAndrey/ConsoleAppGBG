package program.service;



import program.model.User;

import program.repository.UserRepository;
import program.util.DataValidator;

import java.util.List;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(Connection connection) {
        this.userRepository = new UserRepository(connection);
    }

    // Регистрация нового пользователя
    public User registerUser(String login, String password, String userName) throws SQLException {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Проверяем, не занят ли логин
        if (userRepository.findByLogin(login).isPresent()) {
            throw new IllegalStateException("Login already exists");
        }

        // Хешируем пароль (в реальном проекте используйте BCrypt или аналоги)
        String passwordHash = hashPassword(password);

        User user = new User();
        user.setLogin(login);
        user.setPassword(""); // Не храним чистый пароль
        user.setPasswordHash(passwordHash);
        user.setUserName(userName);
        user.setIsActive(true);

        System.out.println("Новый пользователь!");

        return userRepository.create(user);
    }

    // Аутентификация пользователя
    public Optional<User> authenticate(String login, String password) throws SQLException {
        if (login == null || password == null) {
            return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findByLogin(login);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        if (!user.getIsActive()) {
            return Optional.empty();
        }

        // Проверяем пароль
        System.out.println(user.getPasswordHash());
        if (verifyPassword(password, user.getPasswordHash())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    // Получение пользователя по ID
    public Optional<User> getUserById(long userId) throws SQLException {
        return userRepository.findById(userId);
    }

    // Поиск пользователей по имени
    public List<User> searchUsersByName(String namePart) throws SQLException {
        // В реальной реализации нужно добавить соответствующий метод в репозиторий
        // с запросом типа: SELECT * FROM users WHERE user_name LIKE ?
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> user.getUserName() != null &&
                        user.getUserName().toLowerCase().contains(namePart.toLowerCase()))
                .toList();
    }

    // Обновление профиля пользователя
    public User updateUserProfile(long userId, String newUserName, String newPassword) throws SQLException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();

        if (newUserName != null && !newUserName.isBlank()) {
            user.setUserName(newUserName);
        }

        if (newPassword != null && !newPassword.isBlank()) {
            user.setPasswordHash(hashPassword(newPassword));
        }

        return userRepository.update(user);
    }

    // Блокировка/разблокировка пользователя
    public User setUserActiveStatus(long userId, boolean isActive) throws SQLException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        user.setIsActive(isActive);
        return userRepository.update(user);
    }

    // Удаление пользователя
    public boolean deleteUser(long userId) throws SQLException {
        return userRepository.delete(userId);
    }

    // Вспомогательный метод для хеширования пароля
    private String hashPassword(String password) {
        // В реальном проекте используйте:
        // return BCrypt.hashpw(password, BCrypt.gensalt());
        return "hash_" + password; // Упрощенная реализация для примера
    }

    // Вспомогательный метод для проверки пароля
    private boolean verifyPassword(String inputPassword, String storedHash) {
        // В реальном проекте используйте:
        // return BCrypt.checkpw(inputPassword, storedHash);
        return ("hash_" + inputPassword).equals(storedHash); // Упрощенная реализация
    }

    // Получение количества активных пользователей
    public long countActiveUsers() throws SQLException {
        // В реальной реализации нужно добавить соответствующий метод в репозиторий
        // с запросом: SELECT COUNT(*) FROM users WHERE is_active = true
        return userRepository.findAll().stream()
                .filter(User::getIsActive)
                .count();
    }
}