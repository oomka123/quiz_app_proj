package services;

import models.User;
import repositories.UserRepository;
import observers.Observer;

import java.util.List;
import java.util.ArrayList;

public class UserService {
    private final UserRepository userRepo;
    private final List<Observer> observers = new ArrayList<>(); // Список наблюдателей

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public String registerUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        User user = new User(username.trim(), password);
        boolean success = userRepo.userRegistration(user);
        if (success) {
            notifyObservers("User registered: " + username); // Уведомляем наблюдателей
            return "Registration successful!";
        } else {
            return "Registration failed. Username may already exist.";
        }
    }

    public User loginUser(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return null;
        }
        return userRepo.userLogin(username, password);
    }

    public void addObserver(Observer observer) {
        observers.add(observer); // Добавляем наблюдателя в список
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer); // Удаляем наблюдателя из списка
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message); // Уведомляем каждого наблюдателя
        }
    }

    public User getUserById(int userId) {
        return userRepo.getUserById(userId);
    }

    public String getUserRole(int userId) {
        return userRepo.getUserRole(userId);
    }

    public boolean updateUserRole(int userId, String newRole) {
        boolean success = userRepo.updateUserRole(userId, newRole);
        if (success) {
            notifyObservers("User role updated for user ID: " + userId); // Уведомляем наблюдателей
        }
        return success;
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }
}