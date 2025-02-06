package application.menus;

import enums.RoleCategory;
import models.User;

public class QuizMenu {
    public void showQuizMenu(User currentUser) {
        System.out.println();
        System.out.println("Quiz Menu");
        System.out.println("Select option:");
        System.out.println("1. Start Quiz");
        System.out.println("2. Create Quiz");
        System.out.println("3. Show Quizzes");
        System.out.println("4. Delete Quizzes");
        System.out.println("5. Manage Questions");

        // Преобразуем строку роли в Enum
        RoleCategory role = RoleCategory.valueOf(currentUser.getRole().name());

        if (role == RoleCategory.ADMIN) {
            System.out.println("6. Admin Panel");
        }
        if (role == RoleCategory.EDITOR || role == RoleCategory.ADMIN) {
            System.out.println("7. Editor Panel");
        }
        System.out.println("0. Logout");
        System.out.println();
        System.out.print("Enter option (0-7): ");
    }
}
