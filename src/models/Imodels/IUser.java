package models.Imodels;

import enums.RoleCategory;

public interface IUser {
    int getUserId();
    void setUserId(int userId);

    String getUserName();
    void setUserName(String userName);

    String getUserPassword();
    void setUserPassword(String userPassword);

    RoleCategory getRole();
    void setRole(RoleCategory role);
}
