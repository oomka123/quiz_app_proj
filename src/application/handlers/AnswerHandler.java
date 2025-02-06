package application.handlers;

import controllers.AnswerController;
import controllers.QuestionController;
import controllers.QuizController;
import models.Answer;
import models.Question;
import models.Quiz;
import models.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AnswerHandler {

    private final QuizController quizController;
    private final QuestionController questionController;
    private final AnswerController answerController;
    private final Scanner scanner;

    public AnswerHandler(QuizController quizController, QuestionController questionController, AnswerController answerController, Scanner scanner) {
        this.quizController = quizController;
        this.answerController = answerController;
        this.questionController = questionController;
        this.scanner = scanner;
    }

    public void viewAnswers(User currentUser) {
        Question selectedQuestion = selectQuestion(currentUser);
        if (selectedQuestion == null) return;

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestion.getQuestionId());
        if (answers.isEmpty()) {
            System.out.println("No answers found.");
        } else {
            answers.forEach(a -> System.out.println(String.format("- %s (Correct: %b)", a.getAnswerText(), a.isCorrectAnswer())));
        }
    }

    public void addAnswer(User currentUser) {
        Question selectedQuestion = selectQuestion(currentUser);
        if (selectedQuestion == null) return;

        System.out.print("Enter answer text: ");
        String answerText = scanner.nextLine();
        System.out.print("Is this answer correct? (true/false): ");

        boolean isCorrect;
        try {
            isCorrect = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.nextLine();
            return;
        }

        Answer answer = new Answer(0, answerText, isCorrect, selectedQuestion.getQuestionId());
        System.out.println(answerController.addAnswer(answer) ? "Answer added successfully." : "Failed to add answer.");
    }

    public void deleteAnswer(User currentUser) {
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

        int answerId = answers.get(answerIndex - 1).getAnswerId();
        System.out.println(answerController.deleteAnswer(answerId) ? "Answer deleted successfully." : "Failed to delete answer.");
    }

    public void updateAnswer(User currentUser) {

        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        displayQuizzes(quizzes);

        System.out.print("Enter the number of the quiz to view questions: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine();

        if (quizIndex <= 0 || quizIndex > quizzes.size()) {
            System.out.println("Invalid quiz number.");
            return;
        }

        int selectedQuizId = quizzes.get(quizIndex - 1).getQuizId();


        List<Question> questions = questionController.getQuestionsByQuiz(selectedQuizId);
        if (questions.isEmpty()) {
            System.out.println("No questions available for this quiz.");
            return;
        }

        displayQuestions(questions);

        System.out.print("Enter the number of the question to view answers: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex <= 0 || questionIndex > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        int selectedQuestionId = questions.get(questionIndex - 1).getQuestionId();

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestionId);
        if (answers.isEmpty()) {
            System.out.println("No answers found for this question.");
            return;
        }


        System.out.println("Answers:");
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            System.out.println((i + 1) + ". " + answer.getAnswerText() + " (Correct: " + answer.isCorrectAnswer() + ")");
        }

        System.out.print("Enter the number of the answer to update: ");
        int answerIndex = scanner.nextInt();
        scanner.nextLine();

        if (answerIndex <= 0 || answerIndex > answers.size()) {
            System.out.println("Invalid answer number.");
            return;
        }

        int answerId = answers.get(answerIndex - 1).getAnswerId();

        System.out.print("Enter new answer text: ");
        String newAnswerText = scanner.nextLine();
        System.out.print("Is this answer correct? (true/false): ");
        boolean newIsCorrect = scanner.nextBoolean();
        scanner.nextLine();

        Answer updatedAnswer = new Answer(answerId, newAnswerText, newIsCorrect, selectedQuestionId);
        if (answerController.updateAnswer(updatedAnswer)) {
            System.out.println("Answer updated successfully.");
        } else {
            System.out.println("Failed to update answer.");
        }
    }

    private Question selectQuestion(User currentUser) {
        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return null;
        }

        displayQuizzes(quizzes);
        int quizIndex = getUserChoice("Enter the number of the quiz: ", quizzes.size());
        if (quizIndex == -1) return null;

        int selectedQuizId = quizzes.get(quizIndex - 1).getQuizId();
        List<Question> questions = questionController.getQuestionsByQuiz(selectedQuizId);
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return null;
        }

        displayQuestions(questions);
        int questionIndex = getUserChoice("Enter the number of the question: ", questions.size());
        if (questionIndex == -1) return null;

        return questions.get(questionIndex - 1);
    }

    private int getUserChoice(String prompt, int max) {
        System.out.print(prompt);
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice <= 0 || choice > max) {
                System.out.println("Invalid choice.");
                return -1;
            }
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            return -1;
        }
    }

    private void displayQuizzes(List<Quiz> quizzes) {
        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }
    }

    private void displayQuestions(List<Question> questions) {
        System.out.println("Questions:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getQuestionText());
        }
    }

    private void displayAnswers(List<Answer> answers) {
        System.out.println("Answers:");
        for (int i = 0; i < answers.size(); i++) {
            System.out.println((i + 1) + ". " + answers.get(i).getAnswerText() + " (Correct: " + answers.get(i).isCorrectAnswer() + ")");
        }
    }
}
