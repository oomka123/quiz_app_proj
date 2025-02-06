package repositories.Irepositories;

import models.User;
import enums.RoleCategory;
import java.util.List;

public interface IUserRepository {
    boolean userRegistration(User user);
    User userLogin(User user);
    User getUserById(int userId);
    String getUserRole(int userId);
    boolean updateUserRole(int userId, RoleCategory newRole);
    List<User> getAllUsers();
}
