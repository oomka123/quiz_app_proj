package controllers.Icontollers;

import models.User;
import enums.RoleCategory;
import java.util.List;

public interface IAuthController {

    String registerUser(String username, String password);

    User loginUser(String username, String password);

    String getUserRole(int userId);

    String updateUserRole(int adminId, int userId, RoleCategory newRole);

    List<User> getAllUsers();

    User getUserById(int userId);
}
