package controllers;

import models.Quiz;
import services.QuizService;

import java.util.List;

public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


    public List<Quiz> getQuizzesByUser(int userId) {
        return quizService.getQuizzesByUser(userId);
    }


    public String createQuiz(String quizName, int userId) {
        return quizService.createQuiz(quizName, userId);
    }


    public List<Quiz> showQuizzes(int userId) {
        return getQuizzesByUser(userId);
    }


    public String deleteQuiz(int quizId) {
        return quizService.deleteQuiz(quizId);
    }

}
