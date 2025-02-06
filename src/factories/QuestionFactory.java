package factories;

public class QuestionFactory {
    public static void validateQuestionData(String text, int quizId) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty.");
        }
        if (quizId <= 0) {
            throw new IllegalArgumentException("Invalid quiz ID.");
        }
    }
}
