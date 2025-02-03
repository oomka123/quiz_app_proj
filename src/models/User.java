package models;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private Role role;


    public enum Role {
        ADMIN, EDITOR, USER
    }


    public User(int userId, String userName, String userPassword, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
    }


    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = Role.USER;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
