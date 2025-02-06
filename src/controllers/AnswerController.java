package controllers;

import controllers.Icontollers.IAnswerController;
import models.Answer;
import services.AnswerService;

import java.util.List;

public class AnswerController implements IAnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public List<Answer> getAnswersByQuestion(int questionId) {
        try {
            return answerService.getAnswersByQuestion(questionId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean addAnswer(Answer answer) {
        try {
            return answerService.addAnswer(answer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAnswer(int answerId) {
        try {
            return answerService.deleteAnswer(answerId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAnswer(Answer answer) {
        try {
            return answerService.updateAnswer(answer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
