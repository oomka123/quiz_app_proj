package models;

public class Answer {
    private int answerId;
    private int questionId;
    private String answerText;
    private boolean correctAnswer;

    public Answer(int answerId, int questionId, String answerText, boolean correctAnswer) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
    }

    public Answer(int answerId, String answerText, boolean correctAnswer) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
    }

    public Answer(int answerId, String answerText, boolean isCorrect, int questionId) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.correctAnswer = isCorrect;
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return this.answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
