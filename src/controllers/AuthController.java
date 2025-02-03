package controllers;

import models.User;
import services.UserService;

import java.util.List;

public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    public String registerUser(String username, String password) {
        return userService.registerUser(username, password);
    }


    public User loginUser(String username, String password) {
        return userService.loginUser(username, password);
    }


    public String getUserRole(int userId) {
        return userService.getUserRole(userId);
    }


    public String updateUserRole(int adminId, int userId, String newRole) {

        User admin = userService.getUserById(adminId);
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return "Access denied! Only ADMIN can change roles.";
        }

        boolean updated = userService.updateUserRole(userId, newRole);
        return updated ? "User role updated successfully!" : "Failed to update role.";
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUserById(int userId) {
        return userService.getUserById(userId);
    }

}
