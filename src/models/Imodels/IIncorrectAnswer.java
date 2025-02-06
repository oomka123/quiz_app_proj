package models.Imodels;

import models.Answer;
import models.Question;

public interface IIncorrectAnswer {
    Question getQuestion();
    Answer getUserAnswer();
    Answer getCorrectAnswer();
}