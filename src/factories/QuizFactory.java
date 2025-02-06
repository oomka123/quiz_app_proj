package factories;

import models.Quiz;

public class QuizFactory {
    public static Quiz createQuiz(String quizName, String category, int userId) {
        if (quizName == null || quizName.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz name cannot be empty.");
        }

        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }

        return new Quiz(quizName, userId, category);
    }
}

