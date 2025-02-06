import application.menus.QuizMenu;

import controllers.AuthController;
import controllers.QuestionController;
import controllers.QuizController;
import controllers.AnswerController;

import application.menus.*;
import application.handlers.*;
import application.StartQuiz;

import models.*;

import java.util.*;

public class MyApplication {

    private final UserHandler userHandler;
    private final QuizHandler quizHandler;
    private final QuestionHandler questionHandler;
    private final AnswerHandler answerHandler;
    private final QuizMenu quizMenu;
    private final StartQuiz startQuiz;

    private final Scanner scanner;
    private User currentUser;

    public MyApplication(AuthController authController, QuizController quizController, QuestionController questionController, AnswerController answerController) {
        this.scanner = new Scanner(System.in);
        this.quizMenu = new QuizMenu();
        this.userHandler = new UserHandler(authController, quizController,scanner);
        this.quizHandler = new QuizHandler(quizController, scanner);
        this.questionHandler = new QuestionHandler(questionController, quizController, scanner);
        this.answerHandler = new AnswerHandler(quizController, questionController, answerController, scanner);
        this.startQuiz = new StartQuiz(quizController, questionController, answerController, scanner);
    }

    public void start() {
        boolean running = true;

        while (running) {
            MainMenu.mainMenu();

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        userHandler.getRegistration();
                        currentUser = userHandler.getCurrentUser();
                        if (currentUser != null) {
                            quizMenuHandler();
                        }
                    }
                    case 2 -> {
                        userHandler.getLogin();
                        currentUser = userHandler.getCurrentUser();
                        if (currentUser != null) {
                            quizMenuHandler();
                        }
                    }
                    case 0 -> {
                        System.out.println("\n*************************");
                        System.out.println("Goodbye!");
                        System.out.println("*************************\n");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer. " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void quizMenuHandler() {
        boolean inQuizMenu = true;

        while (inQuizMenu) {
            quizMenu.showQuizMenu(currentUser);

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> startQuiz.startQuiz(currentUser);
                    case 2 -> quizHandler.createQuiz(currentUser);
                    case 3 -> quizHandler.showQuizzes(currentUser);
                    case 4 -> quizHandler.deleteQuiz(currentUser);
                    case 5 -> questionMenuHandler();
                    case 6 -> {
                        if (UserHandler.RolePermissions.canManageUsers(currentUser)) {
                            adminPanel();
                        } else {
                            System.out.println("Access denied.");
                        }
                    }
                    case 7 -> {
                        if (UserHandler.RolePermissions.canEditContent(currentUser)) {
                            editorPanel();
                        } else {
                            System.out.println("Access denied.");
                        }
                    }
                    case 0 -> {
                        System.out.println("\n*************************");
                        System.out.println("Logging out...");
                        System.out.println("*************************\n");
                        currentUser = null;
                        inQuizMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer: " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void quizMenuHandlerForUser(User selectedUser) {
        User originalUser = currentUser;
        currentUser = selectedUser;

        boolean inQuizMenu = true;
        while (inQuizMenu) {
            quizMenu.showQuizMenu(currentUser);
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> startQuiz.startQuiz(currentUser);
                    case 2 -> quizHandler.createQuiz(currentUser);
                    case 3 -> quizHandler.showQuizzes(currentUser);
                    case 4 -> quizHandler.deleteQuiz(currentUser);
                    case 5 -> questionMenuHandler();
                    case 0 -> {
                        System.out.println("\n*************************");
                        System.out.println("Returning to Editor Panel...");
                        System.out.println("*************************\n");
                        inQuizMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer: " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        currentUser = originalUser;
    }

    private void questionMenuHandler() {
        boolean inQuestionMenu = true;

        while (inQuestionMenu) {
            QuestionMenu.questionMenu();

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> questionHandler.showQuestions(currentUser);
                    case 2 -> questionHandler.addQuestion(currentUser);
                    case 3 -> questionHandler.deleteQuestion(currentUser);
                    case 4 -> handleAnswerMenu();
                    case 0 -> inQuestionMenu = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer: " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void handleAnswerMenu() {
        while (true) {
            AnswerMenu.answerMenu();
            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> answerHandler.viewAnswers(currentUser);
                    case 2 -> answerHandler.addAnswer(currentUser);
                    case 3 -> answerHandler.deleteAnswer(currentUser);
                    case 4 -> answerHandler.updateAnswer(currentUser);
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer. " + e.getMessage());
                scanner.next();
            }
        }
    }

    private void adminPanel() {
        if (!UserHandler.RolePermissions.canManageUsers(currentUser)) {
            System.out.println("Access denied.");
            return;
        }

        boolean inAdminPanel = true;
        while (inAdminPanel) {
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. View All Users");
            System.out.println("2. Change User Role");
            System.out.println("3. Delete User");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> userHandler.viewAllUsers();
                case 2 -> userHandler.changeUserRole();
                case 3 -> userHandler.deleteUser();
                case 0 -> inAdminPanel = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void editorPanel() {
        if (!UserHandler.RolePermissions.canEditContent(currentUser)) {
            System.out.println("Access denied.");
            return;
        }

        boolean inEditorPanel = true;
        while (inEditorPanel) {
            System.out.println("\n=== Editor Panel ===");
            System.out.println("1. View All Users");
            System.out.println("2. Choose User for Editing");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> userHandler.viewAllUsers();
                case 2 -> {
                    User selectedUser = userHandler.chooseUser();
                    if (selectedUser != null) {
                        quizMenuHandlerForUser(selectedUser);
                    } else {
                        System.out.println("No user selected.");
                    }
                }
                case 0 -> inEditorPanel = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

}