package factories;

import enums.RoleCategory;
import models.AbstractUser;
import models.AdminUser;
import models.EditorUser;
import models.RegularUser;

public class UserFactory {

    public static AbstractUser createUser(int userId, String username, String password, RoleCategory role) {
        return switch (role) {
            case ADMIN -> new AdminUser(userId, username, password);
            case EDITOR -> new EditorUser(userId, username, password);
            default -> new RegularUser(userId, username, password);
        };
    }

    public static RegularUser createUser(String username, String password) {
        validateInput(username, password);
        return new RegularUser(0, username, password);
    }

    private static void validateInput(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
    }
}
