package com.gym.service;

import com.gym.dao.GymMerchDAO;
import com.gym.model.GymMerch;
import com.gym.util.AppLogger;

import java.util.List;

public class GymMerchService {

    private final GymMerchDAO gymMerchDAO = new GymMerchDAO();

    // Admin: add new merchandise
    public boolean addMerch(String name, String type, double price, int quantity) {
        GymMerch merch = new GymMerch(0, name, type, price, quantity);
        boolean success = gymMerchDAO.addMerch(merch);
        if (success) {
            AppLogger.getLogger().info("Admin added merch: " + name);
        }
        return success;
    }

    // Admin: update price
    public boolean updatePrice(int merchId, double newPrice) {
        boolean success = gymMerchDAO.updateMerchPrice(merchId, newPrice);
        if (success) {
            AppLogger.getLogger().info("Admin updated the price for merch ID: " + merchId);
        }
        return success;
    }

    // Anyone: view all merchandise
    public List<GymMerch> getAllMerch() {
        return gymMerchDAO.getAllMerch();
    }

    // Admin: print full stock report
    public void printStockReport() {
        List<GymMerch> items = gymMerchDAO.getAllMerch();
        System.out.println("\n MERCHANDISE STOCK REPORT ");
        if (items.isEmpty()) {
            System.out.println("No merchandise found.");
        } else {
            for (GymMerch item : items) {
                System.out.println(item);
            }
        }
        double totalValue = gymMerchDAO.getTotalStockValue();
        System.out.printf("Total Stock Value: $%.2f%n", totalValue);
        System.out.println("==============================================\n");
        AppLogger.getLogger().info("Admin printed stock report. Total value: $" + totalValue);
    }

    // Admin: gets the total stock value
    public double getTotalStockValue() {
        return gymMerchDAO.getTotalStockValue();
    }
}
