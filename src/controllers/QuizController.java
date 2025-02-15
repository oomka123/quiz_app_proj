package controllers;

import controllers.Icontollers.IQuizController;
import models.Quiz;
import services.Iservices.IQuizService;

import java.util.List;
import java.util.Map;

public class QuizController implements IQuizController {
    private final IQuizService quizService;

    public QuizController(IQuizService quizService) {
        this.quizService = quizService;
    }

    @Override
    public List<Quiz> getQuizzesByCategory(String category) {
        return quizService.getQuizzesByCategory(category);
    }

    @Override
    public Map<Integer, Integer> getQuizCounts() {
        return quizService.getQuizCounts();
    }

    @Override
    public List<Quiz> getQuizzesByUser(int userId) {
        return quizService.getQuizzesByUser(userId);
    }

    @Override
    public String createQuiz(String quizName, String category, int userId) {
        return quizService.createQuiz(quizName, category, userId);
    }

    @Override
    public List<Quiz> showQuizzes(int userId) {
        return getQuizzesByUser(userId);
    }

    @Override
    public String deleteQuiz(int quizId) {
        return quizService.deleteQuiz(quizId);
    }

}
