package factories;

import models.Answer;

public class AnswerFactory{

    public static Answer createAnswer(int questionId, String text, boolean isCorrect) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer text cannot be empty.");
        }

        return new Answer(0, text, isCorrect, questionId);
    }
}