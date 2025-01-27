package repositories;

import database.PostgresDB;
import models.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private final PostgresDB db;

    public QuestionRepository(PostgresDB db) {
        this.db = db;
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        List<Question> questions = new ArrayList<>();
        Connection con = null;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "SELECT question_id, question_text FROM questions WHERE quiz_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, quizId);

                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Question question = new Question(
                            rs.getInt("question_id"),
                            quizId,
                            rs.getString("question_text")
                    );
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching questions: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return questions;
    }

    public boolean addQuestion(Question question) {
        Connection con = null;
        boolean result = false;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "INSERT INTO questions (quiz_id, question_text) VALUES (?, ?)";
                PreparedStatement st = con.prepareStatement(sql);

                st.setInt(1, question.getQuizId());
                st.setString(2, question.getQuestionText());

                int rows = st.executeUpdate();
                result = rows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding question: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }

        return result;
    }

    public boolean deleteQuestion(int questionId) {
        Connection con = null;
        boolean result = false;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "DELETE FROM questions WHERE question_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, questionId);

                int rows = st.executeUpdate();
                result = rows > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting question: " + e.getMessage());
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
