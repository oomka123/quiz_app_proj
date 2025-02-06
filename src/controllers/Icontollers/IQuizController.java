package controllers.Icontollers;

import models.Quiz;
import java.util.List;
import java.util.Map;

public interface IQuizController {
    List<Quiz> getQuizzesByCategory(String category);
    Map<Integer, Integer> getQuizCounts();
    List<Quiz> getQuizzesByUser(int userId);
    String createQuiz(String quizName, String category, int userId);
    List<Quiz> showQuizzes(int userId);
    String deleteQuiz(int quizId);
}
