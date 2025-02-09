package application.handlers;

import models.AbstractUser;
import controllers.Icontollers.IAuthController;
import controllers.Icontollers.IQuizController;
import enums.RoleCategory;


import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UserHandler {
    private final IAuthController authController;
    private final IQuizController quizController;
    private final Scanner scanner;
    private AbstractUser currentUser;

    public UserHandler(IAuthController authController, IQuizController quizController, Scanner scanner) {
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

            if ("Registration successful!".equals(response)) {
                currentUser = authController.loginUser(username, password);
                System.out.println(currentUser != null
                        ? "Registration and login successful! Welcome, " + currentUser.getUserName()
                        : "Registration successful, but login failed.");
            } else {
                currentUser = null;
            }
        } catch (Exception e) {
            System.out.println("Error in registration: " + e.getMessage());
            currentUser = null;
        }
    }

    public void getLogin() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            currentUser = authController.loginUser(username, password);
            System.out.println(currentUser != null
                    ? "Login successful! Welcome, " + currentUser.getUserName()
                    : "Invalid username or password. Please try again.");
        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
            currentUser = null;
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
            int quizCount = quizCounts.getOrDefault(user.getUserId(), 0);

            System.out.println("ID: " + user.getUserId() +
                    " | Username: " + user.getUserName() +
                    " | Role: " + role +
                    (currentUser.getRole() == RoleCategory.ADMIN
                            ? " | Password: " + user.getUserPassword()
                            : "") +
                    " | Count of quizzes: " + quizCount);
        });
    }

    public AbstractUser chooseUser() {
        System.out.println("Enter the ID of the user you want to manage:");

        var users = authController.getAllUsers()
                .stream()
                .filter(user -> user.getRole() != RoleCategory.ADMIN)
                .toList();

        if (users.isEmpty()) {
            System.out.println("No users available to manage.");
            return null;
        }

        users.forEach(user -> System.out.println(
                "ID: " + user.getUserId() + " | Username: " + user.getUserName() +
                        " | Role: " + user.getRole().name()));

        System.out.print("Enter user ID, or 0 to cancel: ");
        int selectedId = scanner.nextInt();
        scanner.nextLine();

        if (selectedId == 0) {
            System.out.println("Operation cancelled.");
            return null;
        }

        AbstractUser selectedUser = authController.getUserById(selectedId);
        if (selectedUser == null || selectedUser.getRole() == RoleCategory.ADMIN) {
            System.out.println("Invalid selection. Admin users cannot be managed.");
            return null;
        }

        return selectedUser;
    }


    public static class RolePermissions {
        private static final Set<RoleCategory> CAN_MANAGE_USERS = Set.of(RoleCategory.ADMIN);
        private static final Set<RoleCategory> CAN_EDIT_CONTENT = Set.of(RoleCategory.ADMIN, RoleCategory.EDITOR);

        public static boolean canManageUsers(AbstractUser user) {
            return user != null && CAN_MANAGE_USERS.contains(user.getRole());
        }

        public static boolean canEditContent(AbstractUser user) {
            return user != null && CAN_EDIT_CONTENT.contains(user.getRole());
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

        users.forEach(user -> System.out.println(
                "ID: " + user.getUserId() + " | Username: " + user.getUserName() +
                        " | Role: " + user.getRole().name()));

        System.out.print("Enter the ID of the user you want to delete, or 0 to cancel: ");
        int selectedId = scanner.nextInt();
        scanner.nextLine();

        if (selectedId == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        AbstractUser userToDelete = authController.getUserById(selectedId);
        System.out.println(userToDelete == null
                ? "User with ID " + selectedId + " not found."
                : authController.deleteUser(selectedId));
    }

    public AbstractUser getCurrentUser() {
        return currentUser;
    }

}
