package services.Iservices;

import models.Quiz;
import java.util.List;
import java.util.Map;

public interface IQuizService {
    String createQuiz(String quizName, String category, int userId);
    List<Quiz> getQuizzesByUser(int userId);
    Map<Integer, Integer> getQuizCounts();
    String deleteQuiz(int quizId);
    List<Quiz> getQuizzesByCategory(String category);
}
