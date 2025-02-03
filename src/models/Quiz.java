package models;

public class Quiz {
    private int quizId;
    private int userId;
    private String quizName;
    private int questionCount;


    public Quiz(int quizId, String quizName, int questionCount) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.questionCount = questionCount;
    }

    public Quiz(String quizName, int userId) {
        this.quizName = quizName;
        this.userId = userId;
    }


    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
}
