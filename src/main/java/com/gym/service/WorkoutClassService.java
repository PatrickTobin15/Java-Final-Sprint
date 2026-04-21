package com.gym.service;

import com.gym.dao.WorkoutClassDAO;
import com.gym.model.WorkoutClass;
import com.gym.util.AppLogger;

import java.util.List;

public class WorkoutClassService {

    private final WorkoutClassDAO workoutClassDAO = new WorkoutClassDAO();

    // Trainer: add a new class
    public boolean addClass(String type, String description, int trainerId) {
        WorkoutClass wc = new WorkoutClass(0, type, description, trainerId);
        boolean success = workoutClassDAO.addClass(wc);
        if (success) {
            AppLogger.getLogger().info("Trainer " + trainerId + " added class: " + type);
        }
        return success;
    }

    // Trainer: update a class
    public boolean updateClass(int classId, String newType, String newDescription, int trainerId) {
        WorkoutClass wc = new WorkoutClass(classId, newType, newDescription, trainerId);
        boolean success = workoutClassDAO.updateClass(wc);
        if (success) {
            AppLogger.getLogger().info("Trainer " + trainerId + " updated class ID: " + classId);
        }
        return success;
    }

    // Trainer: delete a class
    public boolean deleteClass(int classId, int trainerId) {
        boolean success = workoutClassDAO.deleteClass(classId, trainerId);
        if (success) {
            AppLogger.getLogger().info("Trainer " + trainerId + " deleted class ID: " + classId);
        }
        return success;
    }

    // Anyone: view all classes
    public List<WorkoutClass> getAllClasses() {
        return workoutClassDAO.getAllClasses();
    }

    // Trainer: view their assigned classes
    public List<WorkoutClass> getMyClasses(int trainerId) {
        return workoutClassDAO.getClassesByTrainer(trainerId);
    }
}
