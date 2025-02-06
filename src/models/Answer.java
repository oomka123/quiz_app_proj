package models;

import models.Imodels.IAnswer;

public class Answer implements IAnswer {
    private int answerId;
    private int questionId;
    private String answerText;
    private boolean correctAnswer;

    public Answer(int answerId, String answerText, boolean isCorrect, int questionId) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.correctAnswer = isCorrect;
        this.questionId = questionId;
    }

    @Override
    public int getAnswerId() {
        return this.answerId;
    }

    @Override
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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
    public String getAnswerText() {
        return this.answerText;
    }

    @Override
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public boolean isCorrectAnswer() {
        return this.correctAnswer;
    }

    @Override
    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean isValid() {
        return answerText != null && !answerText.trim().isEmpty() && questionId > 0;
    }
}
