package com.gym.dao;

import com.gym.model.GymMerch;
import com.gym.util.AppLogger;
import com.gym.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GymMerchDAO {

    // Admin: added a new merchandise item
    public boolean addMerch(GymMerch merch) {
        String sql = "INSERT INTO gymmerch (merchName, merchType, merchPrice, quantityInStock) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, merch.getMerchName());
            stmt.setString(2, merch.getMerchType());
            stmt.setDouble(3, merch.getMerchPrice());
            stmt.setInt(4, merch.getQuantityInStock());

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Merch added: " + merch.getMerchName());
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error adding merch: " + e.getMessage());
            return false;
        }
    }

    // Admin: updated the price of an item
    public boolean updateMerchPrice(int merchId, double newPrice) {
        String sql = "UPDATE gymmerch SET merchPrice=? WHERE merchId=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newPrice);
            stmt.setInt(2, merchId);

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Updated price for merch ID: " + merchId);
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error updating merch price: " + e.getMessage());
            return false;
        }
    }

    // Gets all of the merchandise
    public List<GymMerch> getAllMerch() {
        List<GymMerch> list = new ArrayList<>();
        String sql = "SELECT * FROM gymmerch";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error fetching merch: " + e.getMessage());
        }
        return list;
    }

    // Admin: gets the total value of all of the stock
    public double getTotalStockValue() {
        String sql = "SELECT SUM(merchPrice * quantityInStock) AS total FROM gymmerch";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error calculating stock value: " + e.getMessage());
        }
        return 0.0;
    }

    private GymMerch mapRow(ResultSet rs) throws SQLException {
        return new GymMerch(
                rs.getInt("merchId"),
                rs.getString("merchName"),
                rs.getString("merchType"),
                rs.getDouble("merchPrice"),
                rs.getInt("quantityInStock")
        );
    }
}
