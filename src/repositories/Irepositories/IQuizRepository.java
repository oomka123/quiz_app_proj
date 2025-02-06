package repositories.Irepositories;

import models.Quiz;
import java.util.List;
import java.util.Map;

public interface IQuizRepository {
    boolean quizCreation(Quiz quiz);
    List<Quiz> quizShowing(int userId);
    Map<Integer, Integer> getQuizCountForAllUsers();
    boolean quizDelete(int quizId);
    List<Quiz> getQuizzesByCategory(String category);
}
