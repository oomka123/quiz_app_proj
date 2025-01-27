package models;

public class Quiz {
    private int quizId;
    private int userId;
    private String quizName;

    public Quiz(int quizId, int userId, String quizName) {
        this.quizId = quizId;
        this.userId = userId;
        this.quizName = quizName;
    }

    public Quiz(String quizName, int userId) {
        this.quizName = quizName;
        this.userId = userId;
    }

    public int getQuizId() {
        return this.quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuizName() {
        return this.quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }
}
