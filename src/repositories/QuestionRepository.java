package repositories;

import database.PostgresDB;
import models.Question;
import repositories.Irepositories.IQuestionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository implements IQuestionRepository {

    private final PostgresDB db;

    public QuestionRepository(PostgresDB db) {
        this.db = db;
    }

    @Override
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

    @Override
    public boolean addQuestion(String questionText, int quizId) {
        Connection con = null;
        boolean result = false;

        try {
            con = db.getConnection();
            if (con != null) {
                String sql = "INSERT INTO questions (quiz_id, question_text) VALUES (?, ?)";
                PreparedStatement st = con.prepareStatement(sql);

                st.setInt(1, quizId);
                st.setString(2, questionText);

                int rows = st.executeUpdate();
                result = rows > 0;

                if (result) {

                    updateQuestionCount(quizId);
                }
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

    @Override
    public boolean deleteQuestion(int questionId) {
        Connection con = null;
        boolean result = false;
        int quizId = -1;

        try {
            con = db.getConnection();
            if (con != null) {

                String getQuizIdSql = "SELECT quiz_id FROM questions WHERE question_id = ?";
                PreparedStatement getQuizIdSt = con.prepareStatement(getQuizIdSql);
                getQuizIdSt.setInt(1, questionId);
                ResultSet rs = getQuizIdSt.executeQuery();

                if (rs.next()) {
                    quizId = rs.getInt("quiz_id");
                }


                String sql = "DELETE FROM questions WHERE question_id = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, questionId);

                int rows = st.executeUpdate();
                result = rows > 0;

                if (result) {

                    updateQuestionCount(quizId);
                }
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

    private void updateQuestionCount(int quizId) {
        String sql = "UPDATE quizzez SET question_count = (SELECT COUNT(*) FROM questions WHERE quiz_id = ?) WHERE quiz_id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, quizId);
            st.setInt(2, quizId);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error in updateQuestionCount: " + e.getMessage());
        }
    }

    @Override
    public int getOptionsCount(int questionId) {
        String sql = "SELECT COUNT(*) AS option_count FROM answers WHERE question_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, questionId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("option_count");  // Возвращаем количество вариантов
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getOptionsCount: " + e.getMessage());
        }
        return 0; // Если не найдено вариантов
    }

}
