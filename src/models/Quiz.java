package models;

import models.Imodels.IQuiz;

public class Quiz implements IQuiz {
    private int quizId;
    private int userId;
    private String quizName;
    private int questionCount;
    private String category;

    public Quiz(int quizId, String quizName, int userId, String category, int questionCount) {
        this.quizId = quizId;
        this.userId = userId;
        this.quizName = quizName;
        this.questionCount = questionCount;
        this.category = category;
    }

    public Quiz(int quizId, String quizName, int userId, String category) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.userId = userId;
        this.category = category;
    }

    public Quiz(String quizName, int userId, String category) {
        this.quizName = quizName;
        this.userId = userId;
        this.category = category;
    }

    public Quiz(int quizId, String quizName, int questionCount) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.questionCount = questionCount;
    }

    public Quiz(String quizName, int userId) {
        this.quizName = quizName;
        this.userId = userId;
    }

    public Quiz(String quizName, String category, int userId) {
        this.quizName = quizName;
        this.category = category;
        this.userId = userId;
    }

    @Override
    public int getQuizId() {
        return quizId;
    }

    @Override
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getQuizName() {
        return quizName;
    }

    @Override
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    @Override
    public int getQuestionCount() {
        return questionCount;
    }

    @Override
    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }
}
