package models.Imodels;

public interface IQuiz {
    int getQuizId();
    void setQuizId(int quizId);

    int getUserId();
    void setUserId(int userId);

    String getQuizName();
    void setQuizName(String quizName);

    int getQuestionCount();
    void setQuestionCount(int questionCount);

    String getCategory();
    void setCategory(String category);
}
