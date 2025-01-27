package repositories;

import database.PostgresDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserRepository {
    private final PostgresDB db;

    public UserRepository(PostgresDB db) {
        this.db = db;
    }

    public boolean userRegistration(User user) {
        Connection con = null;
        boolean result = false;

        try {
            con = this.db.getConnection();
            if (con != null) {
                String checkSql = "SELECT COUNT(*) FROM users WHERE user_name = ?";
                PreparedStatement checkSt = con.prepareStatement(checkSql);
                checkSt.setString(1, user.getUser_name());
                ResultSet rs = checkSt.executeQuery();

                if (rs.next() && rs.getInt(1) == 0) {
                    String insertSql = "INSERT INTO users(user_name, user_password) VALUES (?, ?)";
                    PreparedStatement insertSt = con.prepareStatement(insertSql);
                    insertSt.setString(1, user.getUser_name());
                    insertSt.setString(2, user.getUser_password());
                    insertSt.executeUpdate();
                    result = true;
                } else {
                    System.out.println("User already exists.");
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

        return result;
    }


    public User userLogin(String username, String password) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = this.db.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
                st = con.prepareStatement(sql);
                st.setString(1, username);
                st.setString(2, password);
                rs = st.executeQuery();
                if (rs.next()) { // Если пользователь найден
                    int userId = rs.getInt("user_id");
                    String userName = rs.getString("user_name");
                    String userPassword = rs.getString("user_password");
                    return new User(userId, userName, userPassword);
                }
            } else {
                System.out.println("Connection to the database failed!");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close resources: " + e.getMessage());
            }
        }

        return null;
    }
}
