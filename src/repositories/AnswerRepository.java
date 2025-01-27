package repositories;

import database.PostgresDB;
import models.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerRepository {

    private final PostgresDB db;

    public AnswerRepository(PostgresDB db) {
        this.db = db;
    }

    // Получение ответов по ID вопроса
    public List<Answer> getAnswersByQuestion(int questionId) {
        List<Answer> answers = new ArrayList<>();
        Connection con = null;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "SELECT answer_id, answer_text, is_correct FROM answers WHERE question_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, questionId);

                ResultSet rs = st.executeQuery();
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
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return answers;
    }

    // Добавление нового ответа
    public boolean addAnswer(Answer answer) {
        Connection con = null;
        boolean result = false;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "INSERT INTO answers (answer_text, is_correct, question_id) VALUES (?, ?, ?)";
                PreparedStatement st = con.prepareStatement(sql);

                st.setString(1, answer.getAnswerText());
                st.setBoolean(2, answer.isCorrectAnswer());
                st.setInt(3, answer.getQuestionId());

                int rows = st.executeUpdate();
                result = rows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding answer: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return result;
    }

    // Удаление ответа по ID
    public boolean deleteAnswer(int answerId) {
        Connection con = null;
        boolean result = false;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "DELETE FROM answers WHERE answer_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, answerId);

                int rows = st.executeUpdate();
                result = rows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting answer: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return result;
    }

    // Обновление ответа
    public boolean updateAnswer(Answer answer) {
        Connection con = null;
        boolean result = false;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "UPDATE answers SET answer_text = ?, is_correct = ? WHERE answer_id = ?";
                PreparedStatement st = con.prepareStatement(sql);

                st.setString(1, answer.getAnswerText());
                st.setBoolean(2, answer.isCorrectAnswer());
                st.setInt(3, answer.getAnswerId());

                int rows = st.executeUpdate();
                result = rows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error updating answer: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return result;
    }
}
