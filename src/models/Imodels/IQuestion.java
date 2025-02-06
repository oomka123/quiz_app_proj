package models.Imodels;

import java.util.List;
import models.Answer;

public interface IQuestion {
    int getQuestionId();
    void setQuestionId(int questionId);

    int getQuizId();
    void setQuizId(int quizId);

    String getQuestionText();

}
