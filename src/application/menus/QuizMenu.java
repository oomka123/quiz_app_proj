package application.menus;

import application.handlers.UserHandler;
import models.AbstractUser;

public class QuizMenu {
    public void showQuizMenu(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("No user logged in. Returning to main menu...");
            return;
        }

        System.out.println("\nQuiz Menu");
        System.out.println("Select option:");
        System.out.println("1. Start Quiz");
        System.out.println("2. Create Quiz");
        System.out.println("3. Show Quizzes");
        System.out.println("4. Delete Quizzes");
        System.out.println("5. Manage Questions");

        if (UserHandler.RolePermissions.canManageUsers(currentUser)) {
            System.out.println("6. Admin Panel");
        }
        if (UserHandler.RolePermissions.canEditContent(currentUser)) {
            System.out.println("7. Editor Panel");
        }

        System.out.println("0. Logout\n");
        System.out.print("Enter option (0-7): ");
    }
}
