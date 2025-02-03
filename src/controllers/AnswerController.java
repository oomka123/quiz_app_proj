package controllers;

import models.Answer;
import services.AnswerService;

import java.util.List;

public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    public List<Answer> getAnswersByQuestion(int questionId) {
        try {
            return answerService.getAnswersByQuestion(questionId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }
    }


    public boolean addAnswer(Answer answer) {
        try {
            return answerService.addAnswer(answer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    public boolean deleteAnswer(int answerId) {
        try {
            return answerService.deleteAnswer(answerId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    public boolean updateAnswer(Answer answer) {
        try {
            return answerService.updateAnswer(answer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
