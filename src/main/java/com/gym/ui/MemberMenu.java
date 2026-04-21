package com.gym.ui;

import com.gym.model.Membership;
import com.gym.model.User;
import com.gym.model.WorkoutClass;
import com.gym.service.GymMerchService;
import com.gym.service.MembershipService;
import com.gym.service.WorkoutClassService;
import com.gym.util.AppLogger;

import java.util.List;
import java.util.Scanner;

public class MemberMenu {

    private final WorkoutClassService workoutClassService = new WorkoutClassService();
    private final MembershipService membershipService = new MembershipService();
    private final GymMerchService gymMerchService = new GymMerchService();
    private final Scanner scanner;
    private final User member;

    public MemberMenu(Scanner scanner, User member) {
        this.scanner = scanner;
        this.member = member;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n MEMBER MENU ");
            System.out.println("Welcome, " + member.getUserName() + "!");
            System.out.println("1. Browse workout classes");
            System.out.println("2. Purchase a gym membership");
            System.out.println("3. View my memberships & total expenses");
            System.out.println("4. View gym merchandise");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": browseClasses(); break;
                case "2": purchaseMembership(); break;
                case "3": viewMyMemberships(); break;
                case "4": viewMerch(); break;
                case "0":
                    System.out.println("Logging out...");
                    AppLogger.getLogger().info("Member logged out: " + member.getUserName());
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void browseClasses() {
        List<WorkoutClass> classes = workoutClassService.getAllClasses();
        System.out.println("\n WORKOUT CLASSES ");
        if (classes.isEmpty()) {
            System.out.println("No classes available at this time.");
        } else {
            for (WorkoutClass wc : classes) {
                System.out.println(wc);
            }
        }
    }

    private void purchaseMembership() {
        System.out.println("\n Purchase Membership ");
        System.out.println("Available types: Monthly, Annual, Premium");
        System.out.print("Membership type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Cost: $");
        try {
            double cost = Double.parseDouble(scanner.nextLine().trim());
            boolean success = membershipService.purchaseMembership(type, desc, cost, member.getUserId());
            System.out.println(success ? "Membership purchased successfully!" : "Purchase failed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid cost entered.");
        }
    }

    private void viewMyMemberships() {
        List<Membership> memberships = membershipService.getMyMemberships(member.getUserId());
        System.out.println("\n MY MEMBERSHIPS ");
        double total = 0;
        if (memberships.isEmpty()) {
            System.out.println("You have no memberships.");
        } else {
            for (Membership m : memberships) {
                System.out.println(m);
                total += m.getMembershipCost();
            }
        }
        System.out.printf("Total Membership Expenses: $%.2f%n", total);
    }

    private void viewMerch() {
        var items = gymMerchService.getAllMerch();
        System.out.println("\n GYM MERCHANDISE ");
        if (items.isEmpty()) {
            System.out.println("No merchandise available.");
        } else {
            for (var item : items) {
                System.out.println(item);
            }
        }
    }
}
