package controllers;

import controllers.Icontollers.IAuthController;
import models.AbstractUser;
import enums.RoleCategory;
import services.Iservices.IUserAuthService;
import services.Iservices.IUserManagementService;
import java.util.List;

public class AuthController implements IAuthController, IUserManagementService {
    private final IUserAuthService authService;
    private final IUserManagementService managementService;

    public AuthController(IUserAuthService authService, IUserManagementService managementService) {
        this.authService = authService;
        this.managementService = managementService;
    }

    @Override
    public String registerUser(String username, String password) {
        return authService.registerUser(username, password);
    }

    @Override
    public AbstractUser loginUser(String username, String password) {
        return authService.loginUser(username, password);
    }

    @Override
    public String getUserRole(int userId) {
        return managementService.getUserRole(userId);
    }

    @Override
    public String updateUserRole(int adminId, int userId, RoleCategory newRole) {
        return managementService.updateUserRole(adminId, userId, newRole);
    }

    @Override
    public List<AbstractUser> getAllUsers() {
        return managementService.getAllUsers();
    }

    @Override
    public AbstractUser getUserById(int userId) {
        return managementService.getUserById(userId);
    }

    @Override
    public String deleteUser(int userId) {
        return managementService.deleteUser(userId);
    }
}
