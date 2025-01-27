package controllers;

import models.User;
import repositories.UserRepository;

public class AuthController {
    private UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public String registerUser(String username, String password) {
        User user = new User(username, password);
        boolean success = this.userRepo.userRegistration(user);
        return success ? "Registration successful!" : "Registration failed. Username may already exist.";
    }

    public User loginUser(String username, String password) {
        User user = userRepo.userLogin(username, password);
        return user; // Возвращаем пользователя, если вход успешен
    }
}