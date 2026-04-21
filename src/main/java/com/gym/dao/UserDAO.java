package com.gym.dao;

import com.gym.model.*;
import com.gym.util.AppLogger;
import com.gym.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Register a new user
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (userName, password, email, phoneNumber, address, userRole) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword()); // Already hashed by service
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getUserRole());

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Registered user: " + user.getUserName());
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // Find a user by username (for login)
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE userName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToUser(rs);
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error finding user: " + e.getMessage());
        }
        return null;
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error fetching all users: " + e.getMessage());
        }
        return users;
    }

    // Delete a user by ID
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Deleted user ID: " + userId);
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // Helper: map a ResultSet row to the right User subclass
    private User mapRowToUser(ResultSet rs) throws SQLException {
        int id         = rs.getInt("userId");
        String name    = rs.getString("userName");
        String pass    = rs.getString("password");
        String email   = rs.getString("email");
        String phone   = rs.getString("phoneNumber");
        String address = rs.getString("address");
        String role    = rs.getString("userRole");

        switch (role) {
            case "Admin":   return new Admin(id, name, pass, email, phone, address);
            case "Trainer": return new Trainer(id, name, pass, email, phone, address);
            default:        return new Member(id, name, pass, email, phone, address);
        }
    }
}
