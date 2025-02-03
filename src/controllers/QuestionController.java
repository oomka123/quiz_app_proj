package controllers;

import models.Question;
import repositories.QuestionRepository;
import services.QuestionService;

import java.util.List;

public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        try {
            return questionService.getQuestionsByQuiz(quizId);
        } catch (Exception e) {
            System.out.println("Error fetching questions: " + e.getMessage());
            return List.of();
        }
    }

    public String addQuestion(Question question) {
        return questionService.addQuestion(question);
    }

    public String deleteQuestion(int questionId) {
        return questionService.deleteQuestion(questionId);
    }
}