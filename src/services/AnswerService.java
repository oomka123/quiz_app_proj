package services;

import models.Answer;
import repositories.AnswerRepository;

import java.util.List;

public class AnswerService {

    private final AnswerRepository answerRepo;

    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }


    public List<Answer> getAnswersByQuestion(int questionId) {
        if (questionId <= 0) {
            throw new IllegalArgumentException("Question ID must be greater than 0.");
        }
        return answerRepo.getAnswersByQuestion(questionId);
    }


    public boolean addAnswer(Answer answer) {
        if (answer == null || !answer.isValid()) {
            throw new IllegalArgumentException("Answer text cannot be null, empty, or associated with an invalid question.");
        }

        return answerRepo.addAnswer(answer);
    }


    public boolean deleteAnswer(int answerId) {
        if (answerId <= 0) {
            throw new IllegalArgumentException("Invalid answer ID.");
        }

        return answerRepo.deleteAnswer(answerId);
    }


    public boolean updateAnswer(Answer answer) {
        if (answer == null || !answer.isValid()) {
            throw new IllegalArgumentException("Answer cannot be null or invalid.");
        }

        return answerRepo.updateAnswer(answer);
    }
}
