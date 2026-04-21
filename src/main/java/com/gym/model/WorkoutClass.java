package com.gym.model;

public class WorkoutClass {

    private int workoutClassId;
    private String workoutClassType;
    private String workoutClassDescription;
    private int trainerId;

    public WorkoutClass() {}

    public WorkoutClass(int workoutClassId, String workoutClassType,
                        String workoutClassDescription, int trainerId) {
        this.workoutClassId = workoutClassId;
        this.workoutClassType = workoutClassType;
        this.workoutClassDescription = workoutClassDescription;
        this.trainerId = trainerId;
    }

    // Getters
    public int getWorkoutClassId()             { return workoutClassId; }
    public String getWorkoutClassType()        { return workoutClassType; }
    public String getWorkoutClassDescription() { return workoutClassDescription; }
    public int getTrainerId()                  { return trainerId; }

    // Setters
    public void setWorkoutClassId(int id)                  { this.workoutClassId = id; }
    public void setWorkoutClassType(String type)           { this.workoutClassType = type; }
    public void setWorkoutClassDescription(String desc)    { this.workoutClassDescription = desc; }
    public void setTrainerId(int trainerId)                { this.trainerId = trainerId; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s | Trainer ID: %d",
                workoutClassId, workoutClassType, workoutClassDescription, trainerId);
    }
}
