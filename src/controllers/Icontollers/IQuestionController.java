package controllers.Icontollers;

import models.Question;
import java.util.List;

public interface IQuestionController {
    List<Question> getQuestionsByQuiz(int quizId);
    String addQuestion(String questionText, int quizId);
    String deleteQuestion(int questionId);
}
