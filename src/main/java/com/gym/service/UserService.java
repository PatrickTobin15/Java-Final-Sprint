package com.gym.service;

import com.gym.dao.UserDAO;
import com.gym.model.User;
import com.gym.util.AppLogger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    // Register a new user - hashes password before saving
    public boolean register(String username, String plainPassword, String email,
                            String phone, String address, String role) {

        // Check username not already taken
        if (userDAO.findByUsername(username) != null) {
            System.out.println("Username already exists. Please choose another.");
            AppLogger.getLogger().warning("Registration failed - username taken: " + username);
            return false;
        }

        // Hash the password with BCrypt
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        // Create the appropriate subclass based on role
        User user;
        switch (role) {
            case "Admin":   user = new com.gym.model.Admin(0, username, hashedPassword, email, phone, address); break;
            case "Trainer": user = new com.gym.model.Trainer(0, username, hashedPassword, email, phone, address); break;
            default:        user = new com.gym.model.Member(0, username, hashedPassword, email, phone, address); break;
        }

        boolean success = userDAO.registerUser(user);
        if (success) {
            AppLogger.getLogger().info("User registered successfully: " + username + " as " + role);
        }
        return success;
    }

    // Login - verifies hashed password
    public User login(String username, String plainPassword) {
        User user = userDAO.findByUsername(username);

        if (user == null) {
            AppLogger.getLogger().warning("Login failed - user not found: " + username);
            return null;
        }

        if (BCrypt.checkpw(plainPassword, user.getPassword())) {
            AppLogger.getLogger().info("User logged in: " + username);
            return user;
        } else {
            AppLogger.getLogger().warning("Login failed - wrong password for: " + username);
            return null;
        }
    }

    // Admin: get all users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // Admin: delete a user
    public boolean deleteUser(int userId) {
        boolean success = userDAO.deleteUser(userId);
        if (success) {
            AppLogger.getLogger().info("Admin deleted user ID: " + userId);
        }
        return success;
    }
}
