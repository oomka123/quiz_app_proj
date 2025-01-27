package models;

public class IncorrectAnswer {
    private final Question question;
    private final Answer userAnswer;
    private final Answer correctAnswer;

    public IncorrectAnswer(Question question, Answer userAnswer, Answer correctAnswer) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getUserAnswer() {
        return userAnswer;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }
}
