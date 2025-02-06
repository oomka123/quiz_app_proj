package models;

import enums.RoleCategory;
import models.Imodels.IUser;

public class User implements IUser {
    private int userId;
    private String userName;
    private String userPassword;
    private RoleCategory role;


    public enum Role {
        ADMIN, EDITOR, USER
    }


    public User(int userId, String userName, String userPassword, RoleCategory role) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
    }


    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = RoleCategory.USER;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserPassword() {
        return userPassword;
    }

    @Override
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public RoleCategory getRole() {
        return role;
    }

    @Override
    public void setRole(RoleCategory role) {
        this.role = role;
    }
}
