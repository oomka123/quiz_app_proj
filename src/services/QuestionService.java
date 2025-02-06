package services;

import factories.QuestionFactory;
import models.Question;
import repositories.QuestionRepository;
import services.Iservices.IQuestionService;

import java.util.List;

public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepo;

    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Override
    public List<Question> getQuestionsByQuiz(int quizId) {
        if (quizId <= 0) {
            throw new IllegalArgumentException("Quiz ID must be greater than 0.");
        }

        return questionRepo.getQuestionsByQuiz(quizId);
    }

    @Override
    public String addQuestion(String questionText, int quizId) {
        try {
            QuestionFactory.validateQuestionData(questionText, quizId);

            boolean success = questionRepo.addQuestion(questionText, quizId);
            return success ? "Question added successfully!" : "Failed to add question.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public String deleteQuestion(int questionId) {
        if (questionId <= 0) {
            return "Invalid question ID.";
        }

        boolean success = questionRepo.deleteQuestion(questionId);
        return success ? "Question deleted successfully!" : "Failed to delete question.";
    }

}
