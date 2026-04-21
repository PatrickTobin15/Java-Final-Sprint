package com.gym.ui;

import com.gym.model.User;
import com.gym.model.WorkoutClass;
import com.gym.service.GymMerchService;
import com.gym.service.MembershipService;
import com.gym.service.WorkoutClassService;
import com.gym.util.AppLogger;

import java.util.List;
import java.util.Scanner;

public class TrainerMenu {

    private final WorkoutClassService workoutClassService = new WorkoutClassService();
    private final MembershipService membershipService = new MembershipService();
    private final GymMerchService gymMerchService = new GymMerchService();
    private final Scanner scanner;
    private final User trainer;

    public TrainerMenu(Scanner scanner, User trainer) {
        this.scanner = scanner;
        this.trainer = trainer;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== TRAINER MENU ==========");
            System.out.println("Welcome, " + trainer.getUserName() + "!");
            System.out.println("1. View my assigned classes");
            System.out.println("2. Add a workout class");
            System.out.println("3. Update a workout class");
            System.out.println("4. Delete a workout class");
            System.out.println("5. Purchase a gym membership");
            System.out.println("6. View gym merchandise");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": viewMyClasses(); break;
                case "2": addClass(); break;
                case "3": updateClass(); break;
                case "4": deleteClass(); break;
                case "5": purchaseMembership(); break;
                case "6": viewMerch(); break;
                case "0":
                    System.out.println("Logging out...");
                    AppLogger.getLogger().info("Trainer logged out: " + trainer.getUserName());
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewMyClasses() {
        List<WorkoutClass> classes = workoutClassService.getMyClasses(trainer.getUserId());
        System.out.println("\n========== MY CLASSES ==========");
        if (classes.isEmpty()) {
            System.out.println("You have no assigned classes.");
        } else {
            for (WorkoutClass wc : classes) {
                System.out.println(wc);
            }
        }
    }

    private void addClass() {
        System.out.println("\n--- Add Workout Class ---");
        System.out.print("Class type (e.g., Yoga, HIIT, Spin): ");
        String type = scanner.nextLine().trim();
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        boolean success = workoutClassService.addClass(type, desc, trainer.getUserId());
        System.out.println(success ? "Class added successfully!" : "Failed to add class.");
    }

    private void updateClass() {
        viewMyClasses();
        System.out.print("Enter class ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("New class type: ");
            String type = scanner.nextLine().trim();
            System.out.print("New description: ");
            String desc = scanner.nextLine().trim();
            boolean success = workoutClassService.updateClass(id, type, desc, trainer.getUserId());
            System.out.println(success ? "Class updated!" : "Update failed. Check the class ID.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void deleteClass() {
        viewMyClasses();
        System.out.print("Enter class ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Are you sure? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                boolean success = workoutClassService.deleteClass(id, trainer.getUserId());
                System.out.println(success ? "Class deleted." : "Delete failed. Check the class ID.");
            } else {
                System.out.println("Cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private void purchaseMembership() {
        System.out.println("\n--- Purchase Membership ---");
        System.out.println("Available types: Monthly, Annual, Premium");
        System.out.print("Membership type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Cost: $");
        try {
            double cost = Double.parseDouble(scanner.nextLine().trim());
            boolean success = membershipService.purchaseMembership(type, desc, cost, trainer.getUserId());
            System.out.println(success ? "Membership purchased!" : "Purchase failed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid cost entered.");
        }
    }

    private void viewMerch() {
        var items = gymMerchService.getAllMerch();
        System.out.println("\n========== GYM MERCHANDISE ==========");
        if (items.isEmpty()) {
            System.out.println("No merchandise available.");
        } else {
            for (var item : items) {
                System.out.println(item);
            }
        }
    }
}
