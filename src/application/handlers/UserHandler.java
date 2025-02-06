package application.handlers;

import models.User;
import controllers.AuthController;
import controllers.QuizController;
import enums.RoleCategory;


import java.util.Map;
import java.util.Scanner;

public class UserHandler {
    private final AuthController authController;
    private final QuizController quizController;
    private final Scanner scanner;
    private User currentUser;

    public UserHandler(AuthController authController, QuizController quizController, Scanner scanner) {
        this.authController = authController;
        this.quizController = quizController;
        this.scanner = scanner;
    }

    public void getRegistration() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            String response = authController.registerUser(username, password);
            System.out.println(response);

            if (response.equals("Registration successful!")) {
                currentUser = authController.loginUser(username, password);
                if (currentUser != null) {
                    System.out.println("Registration and login successful! Welcome, " + currentUser.getUserName());
                }
            }
        } catch (Exception e) {
            System.out.println("Error in registration: " + e.getMessage());
        }
    }

    public void getLogin() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = authController.loginUser(username, password);
            if (user != null) {
                currentUser = user;
                System.out.println("Login successful! Welcome, " + user.getUserName());
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
        }
    }

    public void viewAllUsers() {
        var users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        Map<Integer, Integer> quizCounts = quizController.getQuizCounts();

        System.out.println("List of Users:");
        users.forEach(user -> {
            RoleCategory role = RoleCategory.valueOf(user.getRole().name());
            int quizCount = quizCounts.getOrDefault(user.getUserId(), 0); // Если квизов нет, ставим 0

            if (currentUser.getRole() == RoleCategory.ADMIN) {
                System.out.println("ID: " + user.getUserId() +
                        " | Username: " + user.getUserName() +
                        " | Role: " + role +
                        " | Password: " + user.getUserPassword() +
                        " | Count of quizzes: " + quizCount);
            } else {
                System.out.println("ID: " + user.getUserId() +
                        " | Username: " + user.getUserName() +
                        " | Role: " + role +
                        " | Count of quizzes: " + quizCount);
            }
        });
    }

    public User chooseUser() {
        System.out.println("Enter the ID of the user you want to manage:");

        var users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return null;
        }

        for (var user : users) {
            System.out.println("ID: " + user.getUserId() +
                    " | Username: " + user.getUserName() +
                    " | Role: " + user.getRole().name());
        }

        System.out.print("Enter user ID, or 0 to cancel: ");
        int selectedId = scanner.nextInt();
        scanner.nextLine();

        if (selectedId == 0) {
            System.out.println("Operation cancelled.");
            return null;
        }

        User selectedUser = authController.getUserById(selectedId);
        if (selectedUser == null) {
            System.out.println("User with ID " + selectedId + " not found.");
        }
        return selectedUser;
    }

    public static class RolePermissions {
        public static boolean canEditContent(User user) {
            return user.getRole() == RoleCategory.ADMIN || user.getRole() == RoleCategory.EDITOR;
        }

        public static boolean canManageUsers(User user) {
            return user.getRole() == RoleCategory.ADMIN;
        }
    }

    public void changeUserRole() {
        if (!RolePermissions.canManageUsers(currentUser)) {
            System.out.println("Access denied.");
            return;
        }

        System.out.print("Enter user ID to change role, or 0 to cancel: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        if (userId == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        System.out.println("Select new role:");
        System.out.println("1. Admin");
        System.out.println("2. Editor");
        System.out.println("3. User");
        System.out.print("Enter role number: ");

        int roleOption = scanner.nextInt();
        scanner.nextLine();

        RoleCategory newRole = RoleCategory.fromInt(roleOption);

        String result = authController.updateUserRole(currentUser.getUserId(), userId, newRole);
        System.out.println(result);
    }

    public void deleteUser() {

        var users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("List of Users:");
        for (var user : users) {
            System.out.println("ID: " + user.getUserId() +
                    " | Username: " + user.getUserName() +
                    " | Role: " + user.getRole().name());
        }

        System.out.print("Enter the ID of the user you want to delete, or 0 to cancel: ");
        int selectedId = scanner.nextInt();
        scanner.nextLine();

        if (selectedId == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        User userToDelete = authController.getUserById(selectedId);
        if (userToDelete == null) {
            System.out.println("User with ID " + selectedId + " not found.");
            return;
        }

        String result = authController.deleteUser(selectedId);

        System.out.println(result);
    }


    public User getCurrentUser() {
        return currentUser;
    }

}
