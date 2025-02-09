package models;

import enums.RoleCategory;
import models.Imodels.IUserActions;

public class RegularUser extends AbstractUser implements IUserActions {
    public RegularUser(int userId, String userName, String userPassword) {
        super(userId, userName, userPassword, RoleCategory.USER);
    }

    @Override
    public boolean canEditQuizzes() {
        return false;
    }
}
