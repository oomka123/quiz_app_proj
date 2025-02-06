package models.Imodels;

public interface IAnswer {
    int getAnswerId();
    void setAnswerId(int answerId);

    int getQuestionId();
    void setQuestionId(int questionId);

    String getAnswerText();
    void setAnswerText(String answerText);

    boolean isCorrectAnswer();
    void setCorrectAnswer(boolean correctAnswer);

    boolean isValid();
}

