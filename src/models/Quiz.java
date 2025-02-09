package models;

import models.Imodels.IQuiz;

public class Quiz {
    private int quizId;
    private int userId;
    private String quizName;
    private int questionCount;
    private String category;

    private Quiz(QuizBuilder builder) {
        this.quizId = builder.quizId;
        this.userId = builder.userId;
        this.quizName = builder.quizName;
        this.questionCount = builder.questionCount;
        this.category = builder.category;
    }


    public int getQuizId() {
        return quizId;
    }

    public int getUserId() {
        return userId;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public String getCategory() {
        return category;
    }

    public static class QuizBuilder {
        private int quizId;
        private int userId;
        private String quizName;
        private int questionCount;
        private String category;

        public QuizBuilder setQuizId(int quizId) {
            this.quizId = quizId;
            return this;
        }

        public QuizBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public QuizBuilder setQuizName(String quizName) {
            this.quizName = quizName;
            return this;
        }

        public QuizBuilder setQuestionCount(int questionCount) {
            this.questionCount = questionCount;
            return this;
        }

        public QuizBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Quiz build() {
            return new Quiz(this);
        }
    }
}
