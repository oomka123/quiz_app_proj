package repositories;

import database.PostgresDB;
import models.Answer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerRepository {

    private final PostgresDB db;

    public AnswerRepository(PostgresDB db) {
        this.db = db;
    }


    public List<Answer> getAnswersByQuestion(int questionId) {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT answer_id, answer_text, is_correct FROM answers WHERE question_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, questionId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Answer answer = new Answer(
                            rs.getInt("answer_id"),
                            rs.getString("answer_text"),
                            rs.getBoolean("is_correct"),
                            questionId
                    );
                    answers.add(answer);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching answers: " + e.getMessage());
            return List.of();
        }

        return answers;
    }


    public boolean addAnswer(Answer answer) {
        String sql = "INSERT INTO answers (answer_text, is_correct, question_id) VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, answer.getAnswerText());
            st.setBoolean(2, answer.isCorrectAnswer());
            st.setInt(3, answer.getQuestionId());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding answer: " + e.getMessage());
            return false;
        }
    }


    public boolean deleteAnswer(int answerId) {
        String sql = "DELETE FROM answers WHERE answer_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, answerId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting answer: " + e.getMessage());
            return false;
        }
    }


    public boolean updateAnswer(Answer answer) {
        String sql = "UPDATE answers SET answer_text = ?, is_correct = ? WHERE answer_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, answer.getAnswerText());
            st.setBoolean(2, answer.isCorrectAnswer());
            st.setInt(3, answer.getAnswerId());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating answer: " + e.getMessage());
            return false;
        }
    }
}
