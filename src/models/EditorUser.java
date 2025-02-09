package models;

import enums.RoleCategory;
import models.Imodels.IUserActions;

public class EditorUser extends AbstractUser implements IUserActions {
    public EditorUser(int userId, String userName, String userPassword) {
        super(userId, userName, userPassword, RoleCategory.EDITOR);
    }

    @Override
    public boolean canEditQuizzes() {
        return true;
    }
}
