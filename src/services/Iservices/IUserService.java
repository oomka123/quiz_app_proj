package services.Iservices;

import models.User;
import enums.RoleCategory;
import java.util.List;

public interface IUserService {
    String registerUser(String username, String password);
    User loginUser(String username, String password);
    User getUserById(int userId);
    String getUserRole(int userId);
    boolean updateUserRole(int userId, String newRole);
    List<User> getAllUsers();
}