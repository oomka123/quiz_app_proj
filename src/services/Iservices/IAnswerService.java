package services.Iservices;

import models.Answer;
import java.util.List;

public interface IAnswerService {
    List<Answer> getAnswersByQuestion(int questionId);
    boolean addAnswer(Answer answer);
    boolean deleteAnswer(int answerId);
    boolean updateAnswer(Answer answer);
}
