package application.handlers;

import controllers.QuestionController;
import controllers.QuizController;
import models.Question;
import models.Quiz;
import models.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class QuestionHandler {
    private final QuestionController questionController;
    private final QuizController quizController;
    private final Scanner scanner;

    public QuestionHandler(QuestionController questionController, QuizController quizController, Scanner scanner) {
        this.questionController = questionController;
        this.quizController = quizController;
        this.scanner = scanner;
    }

    public void addQuestion(User currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to add a question.");
            return;
        }

        System.out.println("Select quiz to add a question:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available to add questions.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                System.out.print("Enter question text: ");
                String questionText = scanner.nextLine();

                //Question question = new Question(questionText, quizId);
                String response = questionController.addQuestion(questionText, quizId);
                System.out.println(response);
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }

    public void showQuestions(User currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to view questions.");
            return;
        }

        System.out.println("Select quiz to view questions:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                List<Question> questions = questionController.getQuestionsByQuiz(quizId);
                if (questions.isEmpty()) {
                    System.out.println("No questions found for this quiz.");
                } else {
                    System.out.println("Questions:");
                    questions.forEach(q -> System.out.println("- " + q.getQuestionText()));
                }
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }

    public void deleteQuestion(User currentUser) {
        if (currentUser == null) {
            System.out.println("You must be logged in to delete a question.");
            return;
        }

        System.out.println("Select quiz to delete a question:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                List<Question> questions = questionController.getQuestionsByQuiz(quizId);
                if (questions.isEmpty()) {
                    System.out.println("No questions found for this quiz.");
                    return;
                }

                for (int i = 0; i < questions.size(); i++) {
                    System.out.println((i + 1) + ". " + questions.get(i).getQuestionText());
                }

                System.out.print("Enter the number of the question to delete: ");
                int questionNumber = scanner.nextInt();
                scanner.nextLine();

                if (questionNumber > 0 && questionNumber <= questions.size()) {
                    int questionId = questions.get(questionNumber - 1).getQuestionId();
                    String response = questionController.deleteQuestion(questionId);
                    System.out.println(response);
                } else {
                    System.out.println("Invalid question number.");
                }
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }
}
