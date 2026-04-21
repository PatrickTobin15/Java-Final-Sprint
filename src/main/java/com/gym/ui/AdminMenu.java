package com.gym.ui;

import com.gym.model.Membership;
import com.gym.model.User;
import com.gym.service.GymMerchService;
import com.gym.service.MembershipService;
import com.gym.service.UserService;
import com.gym.util.AppLogger;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final UserService userService = new UserService();
    private final MembershipService membershipService = new MembershipService();
    private final GymMerchService gymMerchService = new GymMerchService();
    private final Scanner scanner;
    private final User admin;

    public AdminMenu(Scanner scanner, User admin) {
        this.scanner = scanner;
        this.admin = admin;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n ADMIN MENU ");
            System.out.println("Welcome, " + admin.getUserName() + "!");
            System.out.println("1. View all users");
            System.out.println("2. Delete a user");
            System.out.println("3. View all memberships & total revenue");
            System.out.println("4. Add merchandise item");
            System.out.println("5. Update merchandise price");
            System.out.println("6. Print merchandise stock report");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": viewAllUsers(); break;
                case "2": deleteUser(); break;
                case "3": viewMembershipsAndRevenue(); break;
                case "4": addMerch(); break;
                case "5": updateMerchPrice(); break;
                case "6": gymMerchService.printStockReport(); break;
                case "0":
                    System.out.println("Logging out...");
                    AppLogger.getLogger().info("Admin logged out: " + admin.getUserName());
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("\n ALL USERS ");
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User u : users) {
                System.out.println(u);
            }
        }
    }

    private void deleteUser() {
        viewAllUsers();
        System.out.print("Please Enter a user ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Are you sure you want to delete this user ID " + id + "? (yes/no): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = userService.deleteUser(id);
                System.out.println(success ? "User has been deleted successfully." : "Could not delete the user.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID entered.");
        }
    }

    private void viewMembershipsAndRevenue() {
        List<Membership> memberships = membershipService.getAllMemberships();
        System.out.println("\n ALL MEMBERSHIPS ");
        if (memberships.isEmpty()) {
            System.out.println("There is no memberships found.");
        } else {
            for (Membership m : memberships) {
                System.out.println(m);
            }
        }
        double revenue = membershipService.getTotalRevenue();
        System.out.printf("%nTotal Annual Revenue: $%.2f%n", revenue);
    }

    private void addMerch() {
        System.out.println("\n Add New Merchandise ");
        System.out.print("Item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Item type (gear/drink/food): ");
        String type = scanner.nextLine().trim();
        System.out.print("Price: $");
        try {
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Quantity in stock: ");
            int qty = Integer.parseInt(scanner.nextLine().trim());
            boolean success = gymMerchService.addMerch(name, type, price, qty);
            System.out.println(success ? "The item has been added successfully!" : "Failed to add the item.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid price or quantity entered.");
        }
    }

    private void updateMerchPrice() {
        gymMerchService.printStockReport();
        System.out.print("Enter merch ID to update price: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("New price: $");
            double price = Double.parseDouble(scanner.nextLine().trim());
            boolean success = gymMerchService.updatePrice(id, price);
            System.out.println(success ? "Price updated!" : "Update failed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
