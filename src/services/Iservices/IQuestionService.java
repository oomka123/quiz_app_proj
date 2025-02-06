package services.Iservices;

import models.Question;
import java.util.List;

public interface IQuestionService {
    List<Question> getQuestionsByQuiz(int quizId);
    String addQuestion(String questionText, int quizId);
    String deleteQuestion(int questionId);
}