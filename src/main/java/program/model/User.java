package program.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String login;
    private String password;  // Обычно не хранится в открытом виде, только passwordHash
    private String passwordHash;
    private String userName;
    private Boolean isActive;
//    private LocalDateTime createdAt;  // Обычно такие поля есть в таблицах
//    private LocalDateTime updatedAt;

    // Конструкторы
    public User() {
    }

    public User(Long userId, String login, String password, String passwordHash,
                String userName, Boolean isActive) {
//        this.id = userId;
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.passwordHash = passwordHash;
        this.userName = userName;
        this.isActive = isActive;
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

/*    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }*/

    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(login, user.login) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(isActive, user.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, passwordHash, userName, isActive);
    }

    // toString
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", userName='" + userName + '\'' +
                ", isActive=" + isActive +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
                '}';
    }
}