package services;

import models.User;
import factories.UserFactory;
import repositories.UserRepository;
import enums.RoleCategory;
import services.Iservices.IUserService;

import java.util.List;

public class UserService implements IUserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String registerUser(String username, String password) {
        try {
            User user = UserFactory.createUser(username, password);
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
    public User loginUser(String username, String password) {
        try {
            User user = UserFactory.createUser(username, password);
            return userRepo.userLogin(user);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserById(int userId) {
        return userRepo.getUserById(userId);
    }

    @Override
    public String getUserRole(int userId) {
        return userRepo.getUserRole(userId);
    }

    @Override
    public boolean updateUserRole(int userId, String newRole) {
        RoleCategory roleCategory;

        try {
            roleCategory = RoleCategory.valueOf(newRole.toUpperCase()); // Преобразуем строку в Enum
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role: " + newRole);
            return false;
        }

        boolean success = userRepo.updateUserRole(userId, roleCategory); // Передаём Enum, а не строку
        return success;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public boolean deleteUser(int userId) {
        return userRepo.deleteUser(userId);
    }
}