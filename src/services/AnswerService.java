package services;

import models.Answer;
import repositories.AnswerRepository;
import factories.AnswerFactory;
import repositories.Irepositories.IAnswerRepository;
import services.Iservices.IAnswerService;

import java.util.List;

public class AnswerService implements IAnswerService {

    private final IAnswerRepository answerRepo;

    public AnswerService(IAnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    @Override
    public List<Answer> getAnswersByQuestion(int questionId) {
        if (questionId <= 0) {
            throw new IllegalArgumentException("Question ID must be greater than 0.");
        }
        return answerRepo.getAnswersByQuestion(questionId);
    }

    @Override
    public boolean addAnswer(Answer answer) {
        try {
            Answer validatedAnswer = AnswerFactory.createAnswer(answer.getQuestionId(), answer.getAnswerText(), answer.isCorrectAnswer());
            return answerRepo.addAnswer(validatedAnswer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding answer: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAnswer(int answerId) {
        if (answerId <= 0) {
            throw new IllegalArgumentException("Invalid answer ID.");
        }

        return answerRepo.deleteAnswer(answerId);
    }

    @Override
    public boolean updateAnswer(Answer answer) {
        if (answer == null || !answer.isValid()) {
            throw new IllegalArgumentException("Answer cannot be null or invalid.");
        }

        return answerRepo.updateAnswer(answer);
    }
}
