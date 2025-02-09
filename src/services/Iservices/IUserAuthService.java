package services.Iservices;

import models.AbstractUser;

public interface IUserAuthService {
    String registerUser(String username, String password);
    AbstractUser loginUser(String username, String password);
}