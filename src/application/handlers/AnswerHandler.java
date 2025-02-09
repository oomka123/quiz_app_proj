package application.handlers;

import controllers.Icontollers.IAnswerController;
import controllers.Icontollers.IQuestionController;
import controllers.Icontollers.IQuizController;

import models.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class AnswerHandler {

    private final IQuizController quizController;
    private final IQuestionController questionController;
    private final IAnswerController answerController;
    private final QuizHandler quizHandler;
    private final QuestionHandler questionHandler;
    private final Scanner scanner;

    public AnswerHandler(IQuizController quizController, IQuestionController questionController,
                         IAnswerController answerController, QuizHandler quizHandler,
                         QuestionHandler questionHandler, Scanner scanner) {
        this.quizController = quizController;
        this.answerController = answerController;
        this.questionController = questionController;
        this.quizHandler = quizHandler;
        this.questionHandler = questionHandler;
        this.scanner = scanner;
    }


    public void viewAnswers(AbstractUser currentUser) {
        Question selectedQuestion = selectQuestion(currentUser);
        if (selectedQuestion == null) return;

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestion.getQuestionId());
        System.out.println(answers.isEmpty() ? "No answers found." : "Answers:");
        answers.forEach(a -> System.out.println("- " + a.getAnswerText() + " (Correct: " + a.isCorrectAnswer() + ")"));
    }

    public void addAnswer(AbstractUser currentUser) {
        Question selectedQuestion = selectQuestion(currentUser);
        if (selectedQuestion == null) return;

        System.out.print("Enter answer text: ");
        String answerText = scanner.nextLine();
        System.out.print("Is this answer correct? (true/false): ");

        try {
            boolean isCorrect = scanner.nextBoolean();
            scanner.nextLine();
            System.out.println(answerController.addAnswer(new Answer(0, answerText, isCorrect, selectedQuestion.getQuestionId()))
                    ? "Answer added successfully." : "Failed to add answer.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.nextLine();
        }
    }

    public void deleteAnswer(AbstractUser currentUser) {
        Question selectedQuestion = selectQuestion(currentUser);
        if (selectedQuestion == null) return;

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestion.getQuestionId());
        if (answers.isEmpty()) {
            System.out.println("No answers found.");
            return;
        }

        displayAnswers(answers);
        int answerIndex = getUserChoice("Enter the number of the answer to delete: ", answers.size());
        if (answerIndex == -1) return;

        System.out.println(answerController.deleteAnswer(answers.get(answerIndex - 1).getAnswerId())
                ? "Answer deleted successfully." : "Failed to delete answer.");
    }

    public void updateAnswer(AbstractUser currentUser) {
        Question selectedQuestion = selectQuestion(currentUser);
        if (selectedQuestion == null) return;

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestion.getQuestionId());
        if (answers.isEmpty()) {
            System.out.println("No answers found.");
            return;
        }

        displayAnswers(answers);
        int answerIndex = getUserChoice("Enter the number of the answer to update: ", answers.size());
        if (answerIndex == -1) return;

        System.out.print("Enter new answer text: ");
        String newAnswerText = scanner.nextLine();
        System.out.print("Is this answer correct? (true/false): ");

        try {
            boolean newIsCorrect = scanner.nextBoolean();
            scanner.nextLine();
            System.out.println(answerController.updateAnswer(
                    new Answer(answers.get(answerIndex - 1).getAnswerId(), newAnswerText, newIsCorrect, selectedQuestion.getQuestionId()))
                    ? "Answer updated successfully." : "Failed to update answer.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.nextLine();
        }
    }

    private Question selectQuestion(AbstractUser currentUser) {
        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return null;
        }

        quizHandler.displayQuizzes(quizzes);
        int quizIndex = getUserChoice("Enter the number of the quiz: ", quizzes.size());
        if (quizIndex == -1) return null;

        List<Question> questions = questionController.getQuestionsByQuiz(quizzes.get(quizIndex - 1).getQuizId());
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return null;
        }

        questionHandler.displayQuestions(questions);
        int questionIndex = getUserChoice("Enter the number of the question: ", questions.size());
        return (questionIndex == -1) ? null : questions.get(questionIndex - 1);
    }

    private int getUserChoice(String prompt, int max) {
        System.out.print(prompt);
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return (choice <= 0 || choice > max) ? -1 : choice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            return -1;
        }
    }

    private void displayAnswers(List<Answer> answers) {
        System.out.println("Answers:");
        IntStream.range(0, answers.size())
                .forEach(i -> System.out.println((i + 1) + ". " + answers.get(i).getAnswerText()
                        + " (Correct: " + answers.get(i).isCorrectAnswer() + ")"));
    }
}
