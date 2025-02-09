package repositories.Irepositories;

import models.AbstractUser;
import enums.RoleCategory;
import java.util.List;

public interface IUserRepository {
    boolean userRegistration(AbstractUser user);
    AbstractUser userLogin(String username, String password);
    AbstractUser getUserById(int userId);
    String getUserRole(int userId);
    boolean updateUserRole(int userId, RoleCategory newRole);
    List<AbstractUser> getAllUsers();
    boolean deleteUser(int userId);
}
