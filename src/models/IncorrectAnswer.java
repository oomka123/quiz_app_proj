package models;

import models.Imodels.IIncorrectAnswer;

public class IncorrectAnswer implements IIncorrectAnswer{
    private final Question question;
    private final Answer userAnswer;
    private final Answer correctAnswer;

    public IncorrectAnswer(Question question, Answer userAnswer, Answer correctAnswer) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public Answer getUserAnswer() {
        return userAnswer;
    }

    @Override
    public Answer getCorrectAnswer() {
        return correctAnswer;
    }
}
