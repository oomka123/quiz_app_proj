package services;

import models.Quiz;
import repositories.QuizRepository;
import factories.QuizFactory;

import java.util.List;

public class QuizService {
    private final QuizRepository quizRepo;

    public QuizService(QuizRepository quizRepo) {
        this.quizRepo = quizRepo;
    }


    public String createQuiz(String quizName, int userId) {
        try {

            Quiz quiz = QuizFactory.createQuiz(quizName, userId);
            boolean success = quizRepo.quizCreation(quiz);
            return success ? "Quiz created successfully!" : "Failed to create quiz.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }


    public List<Quiz> getQuizzesByUser(int userId) {
        return quizRepo.quizShowing(userId);
    }


    public String deleteQuiz(int quizId) {
        if (quizId <= 0) {
            return "Invalid quiz ID.";
        }

        boolean success = quizRepo.quizDelete(quizId);
        return success ? "Quiz deleted successfully!" : "Failed to delete quiz.";
    }
}
