package services;

import models.Question;
import repositories.QuestionRepository;

import java.util.List;

public class QuestionService {

    private final QuestionRepository questionRepo;

    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        if (quizId <= 0) {
            throw new IllegalArgumentException("Quiz ID must be greater than 0.");
        }

        return questionRepo.getQuestionsByQuiz(quizId);
    }

    public String addQuestion(Question question) {
        if (question == null || question.getQuestionText() == null || question.getQuestionText().trim().isEmpty()) {
            return "Question text cannot be empty.";
        }

        boolean success = questionRepo.addQuestion(question);
        return success ? "Question added successfully!" : "Failed to add question.";
    }

    public String deleteQuestion(int questionId) {
        if (questionId <= 0) {
            return "Invalid question ID.";
        }

        boolean success = questionRepo.deleteQuestion(questionId);
        return success ? "Question deleted successfully!" : "Failed to delete question.";
    }
}
