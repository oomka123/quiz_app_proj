package repositories;

import database.PostgresDB;
import models.User;
import models.User.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final PostgresDB db;

    public UserRepository(PostgresDB db) {
        this.db = db;
    }


    public boolean userRegistration(User user) {
        String checkSql = "SELECT COUNT(*) FROM users WHERE user_name = ?";
        String insertSql = "INSERT INTO users(user_name, user_password, role) VALUES (?, ?, ?)";

        try (Connection con = db.getConnection();
             PreparedStatement checkSt = con.prepareStatement(checkSql);
             PreparedStatement insertSt = con.prepareStatement(insertSql)) {

            checkSt.setString(1, user.getUserName());
            ResultSet rs = checkSt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                insertSt.setString(1, user.getUserName());
                insertSt.setString(2, user.getUserPassword());

                insertSt.setString(3, user.getRole().toString());

                return insertSt.executeUpdate() > 0;
            } else {
                System.out.println("User already exists.");
            }
        } catch (SQLException e) {
            System.out.println("SQL error in userRegistration: " + e.getMessage());
        }
        return false;
    }


    public User userLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                String roleStr = rs.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                return new User(userId, userName, userPassword, role);
            }
        } catch (SQLException e) {
            System.out.println("SQL error in userLogin: " + e.getMessage());
        }
        return null;
    }


    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                String roleStr = rs.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                return new User(userId, userName, userPassword, role);
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getUserById: " + e.getMessage());
        }
        return null;
    }


    public String getUserRole(int userId) {
        String sql = "SELECT role FROM users WHERE user_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getUserRole: " + e.getMessage());
        }
        return "UNKNOWN";
    }


    public boolean updateUserRole(int userId, String newRole) {

        if (!newRole.equalsIgnoreCase("ADMIN") &&
                !newRole.equalsIgnoreCase("EDITOR") &&
                !newRole.equalsIgnoreCase("USER")) {
            System.out.println("Invalid role! Allowed roles: ADMIN, EDITOR, USER.");
            return false;
        }

        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newRole.toUpperCase());
            st.setInt(2, userId);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error in updateUserRole: " + e.getMessage());
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                String roleStr = rs.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                users.add(new User(userId, userName, userPassword, role));
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getAllUsers: " + e.getMessage());
        }
        return users;
    }

}
