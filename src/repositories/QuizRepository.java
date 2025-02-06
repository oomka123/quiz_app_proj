package repositories;

import database.PostgresDB;
import models.Quiz;
import repositories.Irepositories.IQuizRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizRepository implements IQuizRepository {

    private final PostgresDB db;

    public QuizRepository(PostgresDB db) {
        this.db = db;
    }

    @Override
    public boolean quizCreation(Quiz quiz) {
        String sql = "INSERT INTO quizzez (quiz_name, user_id, category) VALUES (?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, quiz.getQuizName());
            st.setInt(2, quiz.getUserId());
            st.setString(3, quiz.getCategory());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in quizCreation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Quiz> quizShowing(int userId) {
        List<Quiz> quizzez = new ArrayList<>();
        String sql = "SELECT q.quiz_id, q.quiz_name, q.category, COUNT(qu.question_id) AS question_count " +
                "FROM quizzez q " +
                "LEFT JOIN questions qu ON q.quiz_id = qu.quiz_id " +
                "WHERE q.user_id = ? " +
                "GROUP BY q.quiz_id, q.quiz_name, q.category";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int quizId = rs.getInt("quiz_id");
                    String quizName = rs.getString("quiz_name");
                    String category = rs.getString("category");
                    int questionCount = rs.getInt("question_count");

                    quizzez.add(new Quiz(quizId, quizName, userId, category, questionCount));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error in quizShowing: " + e.getMessage());
        }

        return quizzez;
    }

    @Override
    public Map<Integer, Integer> getQuizCountForAllUsers() {
        Map<Integer, Integer> quizCounts = new HashMap<>();

        String sql = "SELECT user_id, COUNT(quiz_id) AS quiz_count FROM quizzez GROUP BY user_id";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int quizCount = rs.getInt("quiz_count");
                quizCounts.put(userId, quizCount);
            }

        } catch (SQLException e) {
            System.out.println("SQL error in getQuizCountForAllUsers: " + e.getMessage());
        }

        return quizCounts;
    }

    @Override
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

    @Override
    public List<Quiz> getQuizzesByCategory(String category) {
        String sql = "SELECT * FROM quizzez WHERE category = ?";
        List<Quiz> quizzez = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, category);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                quizzez.add(new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("quiz_name"),
                        rs.getInt("user_id"),
                        rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getQuizzesByCategory: " + e.getMessage());
        }
        return quizzez;
    }

}
