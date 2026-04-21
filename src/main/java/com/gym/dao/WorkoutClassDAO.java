package com.gym.dao;

import com.gym.model.WorkoutClass;
import com.gym.util.AppLogger;
import com.gym.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassDAO {

    // Add a new workout class
    public boolean addClass(WorkoutClass wc) {
        String sql = "INSERT INTO workoutclasses (workoutClassType, workoutClassDescription, trainerId) " +
                     "VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wc.getWorkoutClassType());
            stmt.setString(2, wc.getWorkoutClassDescription());
            stmt.setInt(3, wc.getTrainerId());

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Workout class added: " + wc.getWorkoutClassType());
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error adding workout class: " + e.getMessage());
            return false;
        }
    }

    // Update a workout class
    public boolean updateClass(WorkoutClass wc) {
        String sql = "UPDATE workoutclasses SET workoutClassType=?, workoutClassDescription=? " +
                     "WHERE workoutClassId=? AND trainerId=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wc.getWorkoutClassType());
            stmt.setString(2, wc.getWorkoutClassDescription());
            stmt.setInt(3, wc.getWorkoutClassId());
            stmt.setInt(4, wc.getTrainerId());

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Workout class updated ID: " + wc.getWorkoutClassId());
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error updating workout class: " + e.getMessage());
            return false;
        }
    }

    // Delete a workout class
    public boolean deleteClass(int classId, int trainerId) {
        String sql = "DELETE FROM workoutclasses WHERE workoutClassId=? AND trainerId=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, classId);
            stmt.setInt(2, trainerId);

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Workout class deleted ID: " + classId);
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error deleting workout class: " + e.getMessage());
            return false;
        }
    }

    // Get all workout classes
    public List<WorkoutClass> getAllClasses() {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workoutclasses";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                classes.add(mapRow(rs));
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error fetching classes: " + e.getMessage());
        }
        return classes;
    }

    // Get classes assigned to a specific trainer
    public List<WorkoutClass> getClassesByTrainer(int trainerId) {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workoutclasses WHERE trainerId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classes.add(mapRow(rs));
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error fetching trainer classes: " + e.getMessage());
        }
        return classes;
    }

    private WorkoutClass mapRow(ResultSet rs) throws SQLException {
        return new WorkoutClass(
                rs.getInt("workoutClassId"),
                rs.getString("workoutClassType"),
                rs.getString("workoutClassDescription"),
                rs.getInt("trainerId")
        );
    }
}
