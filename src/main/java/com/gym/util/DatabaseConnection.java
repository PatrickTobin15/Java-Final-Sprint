package com.gym.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/gym_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; 

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                AppLogger.getLogger().info("Database connection established.");
            } catch (SQLException e) {
                AppLogger.getLogger().severe("Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                AppLogger.getLogger().info("Database connection closed.");
            }
        } catch (SQLException e) {
            AppLogger.getLogger().warning("Error closing connection: " + e.getMessage());
        }
    }
}
