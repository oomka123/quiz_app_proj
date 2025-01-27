package models;

import java.util.ArrayList;

public class Question {
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

    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return this.quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return this.questionText;
    }
}