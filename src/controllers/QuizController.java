package controllers;

import java.util.List;
import models.Quiz;
import repositories.QuizRepository;

public class QuizController {
    private final QuizRepository quizRepo;

    public QuizController(QuizRepository quizRepo) {
        this.quizRepo = quizRepo;
    }

    public String createQuiz(String quizName, int userId) {
        // Создаем объект Quiz
        Quiz quiz = new Quiz(quizName, userId);

        // Пытаемся создать тест в репозитории
        boolean success = this.quizRepo.quizCreation(quiz, userId);

        // Возвращаем сообщение в зависимости от результата
        return success ? "Quiz creation successful!" : "Quiz creation failed. Please try again.";
    }

    public List<Quiz> showQuizzes(int userId) {
        // Получаем список квизов для пользователя
        return quizRepo.quizShowing(userId);
    }

    public String deleteQuiz(int quizId) {
        boolean success = this.quizRepo.quizDelete(quizId);
        return success ? "Quiz deleted successfully!" : "Failed to delete quiz. Please try again.";
    }

}
