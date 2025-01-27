package controllers;

import models.Question;
import repositories.QuestionRepository;

import java.util.List;

public class QuestionController {

    private final QuestionRepository questionRepo;

    public QuestionController(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        try {
            return questionRepo.getQuestionsByQuiz(quizId);
        } catch (Exception e) {
            System.out.println("Error fetching questions: " + e.getMessage());
            return List.of();
        }
    }

    public String addQuestion(Question question) {
        if (question.getQuestionText() == null || question.getQuestionText().isEmpty()) {
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
