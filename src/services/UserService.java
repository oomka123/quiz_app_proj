package services;

import enums.RoleCategory;
import factories.UserFactory;
import models.AbstractUser;
import models.RegularUser;
import repositories.Irepositories.IUserRepository;
import services.Iservices.IUserAuthService;
import services.Iservices.IUserManagementService;
import java.util.List;

public class UserService implements IUserAuthService, IUserManagementService {

    private final IUserRepository userRepo;

    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String registerUser(String username, String password) {
        try {
            RegularUser user = UserFactory.createUser(username, password);
            if (userRepo.userRegistration(user)) {
                return "Registration successful!";
            } else {
                return "Registration failed. Username may already exist.";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @Override
    public AbstractUser loginUser(String username, String password) {
        try {
            return userRepo.userLogin(username, password);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public AbstractUser getUserById(int userId) {
        return userRepo.getUserById(userId);
    }

    @Override
    public String getUserRole(int userId) {
        return userRepo.getUserRole(userId);
    }

    @Override
    public String updateUserRole(int adminId, int userId, RoleCategory newRole) {
        AbstractUser admin = userRepo.getUserById(adminId);
        if (admin == null || admin.getRole() != RoleCategory.ADMIN) {
            return "Access denied! Only ADMIN can change roles.";
        }
        boolean updated = userRepo.updateUserRole(userId, newRole);
        return updated ? "User role updated successfully!" : "Failed to update role.";
    }

    @Override
    public String deleteUser(int userId) {
        boolean isDeleted = userRepo.deleteUser(userId);
        return isDeleted ? "User deleted successfully!" : "Failed to delete user.";
    }

    @Override
    public List<AbstractUser> getAllUsers() {
        return userRepo.getAllUsers();
    }
}
