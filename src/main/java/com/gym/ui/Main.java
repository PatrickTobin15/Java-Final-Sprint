package com.gym.ui;

import com.gym.model.User;
import com.gym.service.UserService;
import com.gym.util.AppLogger;
import com.gym.util.DatabaseConnection;

import java.util.Scanner;

public class Main {

    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AppLogger.getLogger().info("Application started.");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    Welcome to Gym Management System  ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean appRunning = true;
        while (appRunning) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": handleLogin(); break;
                case "2": handleRegister(); break;
                case "0":
                    System.out.println("Thank you for using the Gym Management System. Goodbye!");
                    AppLogger.getLogger().info("Application exited.");
                    DatabaseConnection.closeConnection();
                    appRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 0.");
            }
        }

        scanner.close();
    }

    private static void handleLogin() {
        System.out.println("\n Login ");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = userService.login(username, password);

        if (user == null) {
            System.out.println("Login failed. Invalid username or password.");
            return;
        }

        System.out.println("Login successful! Role: " + user.getUserRole());

        // Route to correct menu based on role
        switch (user.getUserRole()) {
            case "Admin":
                new AdminMenu(scanner, user).show();
                break;
            case "Trainer":
                new TrainerMenu(scanner, user).show();
                break;
            case "Member":
                new MemberMenu(scanner, user).show();
                break;
            default:
                System.out.println("Unknown role, please contact an administrator.");
        }
    }

    private static void handleRegister() {
        System.out.println("\n Register ");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        System.out.println("Role (Admin / Trainer / Member): ");
        String role = scanner.nextLine().trim();

        // Validate role
        if (!role.equals("Admin") && !role.equals("Trainer") && !role.equals("Member")) {
            System.out.println("Invalid role. Please enter Admin, Trainer, or Member.");
            return;
        }

        boolean success = userService.register(username, password, email, phone, address, role);
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Username may already be taken.");
        }
    }
}
