package models;

import enums.RoleCategory;
import models.Imodels.IUser;

public abstract class AbstractUser implements IUser {
    protected int userId;
    protected String userName;
    protected String userPassword;
    protected RoleCategory role;

    public AbstractUser(int userId, String userName, String userPassword, RoleCategory role) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
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
}
