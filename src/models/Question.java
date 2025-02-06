package models;

import models.Imodels.IQuestion;

import java.util.ArrayList;

public class Question implements IQuestion{
    private int questionId;
    private int quizId;
    private String questionText;
    private ArrayList<Answer> answers;

    public Question(int questionId, int quizId, String questionText) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.questionText = questionText;
    }

    public Question(String questionText, int quizId) {
        this.questionText = questionText;
        this.quizId = quizId;
    }

    public Question(int questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    @Override
    public int getQuestionId() {
        return this.questionId;
    }

    @Override
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public int getQuizId() {
        return this.quizId;
    }

    @Override
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String getQuestionText() {
        return this.questionText;
    }
}