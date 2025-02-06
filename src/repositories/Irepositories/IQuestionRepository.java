package repositories.Irepositories;

import models.Question;
import java.util.List;

public interface IQuestionRepository {
    List<Question> getQuestionsByQuiz(int quizId);
    boolean addQuestion(String questionText, int quizId);
    boolean deleteQuestion(int questionId);
    int getOptionsCount(int questionId);
}
