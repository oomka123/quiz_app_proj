package factories;

import models.Quiz;

public class QuizFactory {

    public static Quiz createQuiz(String quizName, int userId) {
        if (quizName == null || quizName.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz name cannot be empty.");
        }
        return new Quiz(quizName, userId);
    }
}
