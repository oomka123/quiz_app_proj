package models;

import enums.RoleCategory;
import models.Imodels.IAdminActions;

public class AdminUser extends AbstractUser implements IAdminActions {
    public AdminUser(int userId, String userName, String userPassword) {
        super(userId, userName, userPassword, RoleCategory.ADMIN);
    }

    @Override
    public boolean canEditQuizzes() {
        return true;
    }

    @Override
    public boolean canDeleteUsers() {
        return true;
    }
}
