package services.Iservices;

import enums.RoleCategory;
import models.AbstractUser;
import java.util.List;

public interface IUserManagementService {
    List<AbstractUser> getAllUsers();
    AbstractUser getUserById(int userId);
    String deleteUser(int userId);
    String updateUserRole(int adminId, int userId, RoleCategory newRole);
    String getUserRole(int userId);
}
