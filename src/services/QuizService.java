package services;

import models.Quiz;
import repositories.QuizRepository;
import factories.QuizFactory;
import services.Iservices.IQuizService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizService implements IQuizService {
    private final QuizRepository quizRepo;

    public QuizService(QuizRepository quizRepo) {
        this.quizRepo = quizRepo;
    }

    @Override
    public String createQuiz(String quizName, String category, int userId) {
        try {
            Quiz quiz = QuizFactory.createQuiz(quizName, category, userId);
            if (quizRepo.quizCreation(quiz)) {
                return "Quiz created successfully!";
            } else {
                return "Failed to create quiz.";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<Quiz> getQuizzesByUser(int userId) {
        return quizRepo.quizShowing(userId);
    }

    @Override
    public Map<Integer, Integer> getQuizCounts() {
        return quizRepo.getQuizCountForAllUsers();
    }

    @Override
    public String deleteQuiz(int quizId) {
        if (quizId <= 0) {
            return "Invalid quiz ID.";
        }

        if (quizRepo.quizDelete(quizId)) {
            return "Quiz deleted successfully!";
        } else {
            return "Failed to delete quiz.";
        }
    }

    @Override
    public List<Quiz> getQuizzesByCategory(String category) {
        return quizRepo.getQuizzesByCategory(category);
    }
}