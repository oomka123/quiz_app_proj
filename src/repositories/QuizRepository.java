package repositories;

import database.PostgresDB;
import models.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private PostgresDB db;

    public QuizRepository(PostgresDB db) {
        this.db = db;
    }


    public boolean quizCreation(Quiz quiz) {
        String sql = "INSERT INTO quizzez (quiz_name, user_id) VALUES (?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, quiz.getQuizName());
            st.setInt(2, quiz.getUserId());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in quizCreation: " + e.getMessage());
            return false;
        }
    }


    public List<Quiz> quizShowing(int userId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.quiz_id, q.quiz_name, COUNT(qu.question_id) AS question_count " +
                "FROM quizzez q " +
                "LEFT JOIN questions qu ON q.quiz_id = qu.quiz_id " +
                "WHERE q.user_id = ? " +
                "GROUP BY q.quiz_id, q.quiz_name";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int quizId = rs.getInt("quiz_id");
                    String quizName = rs.getString("quiz_name");
                    int questionCount = rs.getInt("question_count");
                    quizzes.add(new Quiz(quizId, quizName, questionCount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizzes;
    }


    public boolean quizDelete(int quizId) {
        String sql = "DELETE FROM quizzez WHERE quiz_id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, quizId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in quizDelete: " + e.getMessage());
            return false;
        }
    }

}
