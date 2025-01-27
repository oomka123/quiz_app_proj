package repositories;

import database.PostgresDB;
import models.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private final PostgresDB db;

    public QuizRepository(PostgresDB db) {
        this.db = db;
    }

    public boolean quizCreation(Quiz quiz, int userId) {
        Connection con = null;
        boolean result;

        try {
            con = this.db.getConnection();
            if (con != null) {
                String sql = "INSERT INTO quizzez(quiz_name, user_id) VALUES (?, ?)";
                PreparedStatement st = con.prepareStatement(sql);

                st.setString(1, quiz.getQuizName());
                st.setInt(2, userId);

                st.executeUpdate();
                result = true;
            } else {
                System.out.println("Connection to the database failed!");
                result = false;
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return result;
    }

    public List<Quiz> quizShowing(int userId) {
        List<Quiz> quizzes = new ArrayList<>();
        Connection con = null;

        try {
            con = this.db.getConnection();
            if (con != null) {
                String sql = "SELECT quiz_id, quiz_name FROM quizzez WHERE user_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, userId);

                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    int quizId = rs.getInt("quiz_id");
                    String quizName = rs.getString("quiz_name");
                    quizzes.add(new Quiz(quizId, userId, quizName));
                }
            } else {
                System.out.println("Connection to the database failed!");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return quizzes;
    }

    public boolean quizDelete(int quizId) {
        Connection con = null;
        boolean result = false;

        try {
            con = this.db.getConnection();
            if (con != null) {
                String sql = "DELETE FROM quizzez WHERE quiz_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, quizId);
                int rowsAffected = st.executeUpdate();
                result = rowsAffected > 0; // Успешное удаление, если строка была удалена
            } else {
                System.out.println("Connection to the database failed!");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return result;
    }

}
