package application.handlers;

import controllers.Icontollers.IQuizController;
import models.AbstractUser;
import models.Quiz;
import enums.QuizCategory;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class QuizHandler {

    private final IQuizController quizController;
    private final Scanner scanner;

    public QuizHandler(IQuizController quizController, Scanner scanner) {
        this.scanner = scanner;
        this.quizController = quizController;
    }

    public void createQuiz(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to create a quiz.");
            return;
        }

        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine().trim();

        System.out.println("Select a category:");
        QuizCategory[] categories = QuizCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + " - " + categories[i].name());
        }

        System.out.print("Enter category number: ");
        int categoryIndex = scanner.nextInt();
        scanner.nextLine();

        if (categoryIndex < 1 || categoryIndex > categories.length) {
            System.out.println("Invalid category selection.");
            return;
        }

        QuizCategory selectedCategory = categories[categoryIndex - 1];

        String response = quizController.createQuiz(quizName, selectedCategory.name(), currentUser.getUserId());
        System.out.println(response);
    }


    public void showQuizzes(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to view quizzes.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());

        System.out.println(quizzes.isEmpty() ? "No quizzes found." : "Your Quizzes with Question Count:");
        quizzes.forEach(quiz -> System.out.println(
                quiz.getQuizName() + " - " + quiz.getCategory() + " - " + quiz.getQuestionCount() + " questions"
        ));
    }

    public void deleteQuiz(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to delete quizzes.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());

        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available to delete.");
            return;
        }

        System.out.println("Select quiz to delete:");
        IntStream.range(0, quizzes.size()).forEach(i ->
                System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName())
        );

        try {
            System.out.print("Enter the number of the quiz to delete: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                System.out.println(quizController.deleteQuiz(quizzes.get(quizNumber - 1).getQuizId()));
            } else {
                System.out.println("Invalid quiz number. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer: " + e.getMessage());
            scanner.next();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void displayQuizzes(List<Quiz> quizzes) {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }
        System.out.println("Available Quizzes:");
        IntStream.range(0, quizzes.size())
                .forEach(i -> System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName()));
    }

}
