package application.handlers;

import controllers.Icontollers.IQuestionController;
import controllers.Icontollers.IQuizController;
import models.Question;
import models.Quiz;
import models.AbstractUser;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class QuestionHandler {
    private final IQuestionController questionController;
    private final IQuizController quizController;
    private final Scanner scanner;

    public QuestionHandler(IQuestionController questionController, IQuizController quizController, Scanner scanner) {
        this.questionController = questionController;
        this.quizController = quizController;
        this.scanner = scanner;
    }

    public void addQuestion(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to add a question.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available to add questions.");
            return;
        }

        System.out.println("Select quiz to add a question:");
        IntStream.range(0, quizzes.size())
                .forEach(i -> System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName()));

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                System.out.print("Enter question text: ");
                String questionText = scanner.nextLine();

                System.out.println(questionController.addQuestion(questionText, quizId));
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }


    public void showQuestions(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to view questions.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Select quiz to view questions:");
        IntStream.range(0, quizzes.size())
                .forEach(i -> System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName()));

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                List<Question> questions = questionController.getQuestionsByQuiz(quizzes.get(quizNumber - 1).getQuizId());

                System.out.println(questions.isEmpty()
                        ? "No questions found for this quiz."
                        : "Questions:");
                questions.forEach(q -> System.out.println("- " + q.getQuestionText()));
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }


    public void deleteQuestion(AbstractUser currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to delete a question.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Select quiz to delete a question:");
        IntStream.range(0, quizzes.size())
                .forEach(i -> System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName()));

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                List<Question> questions = questionController.getQuestionsByQuiz(quizzes.get(quizNumber - 1).getQuizId());

                if (questions.isEmpty()) {
                    System.out.println("No questions found for this quiz.");
                    return;
                }

                System.out.println("Select question to delete:");
                IntStream.range(0, questions.size())
                        .forEach(i -> System.out.println((i + 1) + ". " + questions.get(i).getQuestionText()));

                System.out.print("Enter the number of the question to delete: ");
                int questionNumber = scanner.nextInt();
                scanner.nextLine();

                System.out.println((questionNumber > 0 && questionNumber <= questions.size())
                        ? questionController.deleteQuestion(questions.get(questionNumber - 1).getQuestionId())
                        : "Invalid question number.");
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }

    public void displayQuestions(List<Question> questions) {
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }
        System.out.println("Questions:");
        IntStream.range(0, questions.size())
                .forEach(i -> System.out.println((i + 1) + ". " + questions.get(i).getQuestionText()));
    }

}
