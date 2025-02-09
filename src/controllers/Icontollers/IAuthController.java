package controllers.Icontollers;

import models.AbstractUser;
import enums.RoleCategory;
import java.util.List;

public interface IAuthController {

    String registerUser(String username, String password);

    AbstractUser loginUser(String username, String password);

    String getUserRole(int userId);

    String updateUserRole(int adminId, int userId, RoleCategory newRole);

    List<AbstractUser> getAllUsers();

    AbstractUser getUserById(int userId);

    String deleteUser(int userId);
}
