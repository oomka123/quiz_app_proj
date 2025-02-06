package application;

import models.Answer;
import models.IncorrectAnswer;
import models.Question;
import models.Quiz;
import controllers.QuizController;
import controllers.QuestionController;
import controllers.AnswerController;
import models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class StartQuiz {

    private final QuizController quizController;
    private final QuestionController questionController;
    private final AnswerController answerController;
    private final Scanner scanner;

    public StartQuiz(QuizController quizController, QuestionController questionController,
                     AnswerController answerController, Scanner scanner) {
        this.quizController = quizController;
        this.questionController = questionController;
        this.answerController = answerController;
        this.scanner = scanner;
    }

    public void startQuiz(User currentUser) {
        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        int quizIndex = getIntInput("Select a quiz by number: ", 1, quizzes.size());
        int quizId = quizzes.get(quizIndex - 1).getQuizId();

        boolean restartQuiz;
        do {
            restartQuiz = false;
            List<Question> allQuestions = questionController.getQuestionsByQuiz(quizId);

            if (allQuestions.isEmpty()) {
                System.out.println("No questions available for this quiz.");
                return;
            }

            List<Question> questionsWithAnswers = new ArrayList<>();
            for (Question question : allQuestions) {
                List<Answer> answers = answerController.getAnswersByQuestion(question.getQuestionId());
                if (!answers.isEmpty()) {
                    questionsWithAnswers.add(question);
                }
            }

            if (questionsWithAnswers.isEmpty()) {
                System.out.println("No valid questions (with answers) available for this quiz.");
                return;
            }

            System.out.println("Starting the quiz...");

            Collections.shuffle(questionsWithAnswers);
            int totalQuestions = questionsWithAnswers.size();
            int correctAnswers = 0;
            List<IncorrectAnswer> incorrectAnswers = new ArrayList<>();
            // qwe
            for (int i = 0; i < totalQuestions; i++) {
                Question question = questionsWithAnswers.get(i);
                System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());

                List<Answer> answers = answerController.getAnswersByQuestion(question.getQuestionId());
                Collections.shuffle(answers);

                for (int j = 0; j < answers.size(); j++) {
                    System.out.println((j + 1) + ". " + answers.get(j).getAnswerText());
                }

                int answerIndex = getIntInput("Enter the number of your answer: ", 1, answers.size());
                Answer userAnswer = answers.get(answerIndex - 1);

                if (userAnswer.isCorrectAnswer()) {
                    System.out.println("Correct!");
                    correctAnswers++;
                } else {
                    System.out.println("Wrong.");
                    Answer correctAnswer = answers.stream().filter(Answer::isCorrectAnswer).findFirst().orElse(null);
                    incorrectAnswers.add(new IncorrectAnswer(question, userAnswer, correctAnswer));
                }

                System.out.println();
            }

            System.out.println("Quiz Finished!");
            System.out.println("You got " + correctAnswers + " out of " + totalQuestions + " questions correct.");

            if (!incorrectAnswers.isEmpty()) {
                System.out.print("Would you like to review the questions you answered incorrectly? (yes/no): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    for (IncorrectAnswer incorrect : incorrectAnswers) {
                        System.out.println("Question: " + incorrect.getQuestion().getQuestionText());
                        System.out.println("Your Answer: " + incorrect.getUserAnswer().getAnswerText());
                        System.out.println("Correct Answer: " +
                                (incorrect.getCorrectAnswer() != null ? incorrect.getCorrectAnswer().getAnswerText() : "No correct answer found."));
                        System.out.println();
                    }
                }
            }

            System.out.print("Would you like to restart this quiz? (yes/no): ");
            restartQuiz = scanner.nextLine().trim().equalsIgnoreCase("yes");

        } while (restartQuiz);

        System.out.println("Thank you for taking the quiz!");
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
        }
    }
}
