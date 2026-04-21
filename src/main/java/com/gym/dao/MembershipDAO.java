package com.gym.dao;

import com.gym.model.Membership;
import com.gym.util.AppLogger;
import com.gym.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {

    // Purchase / add a new membership
    public boolean addMembership(Membership membership) {
        String sql = "INSERT INTO memberships (membershipType, membershipDescription, membershipCost, memberId) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getMembershipType());
            stmt.setString(2, membership.getMembershipDescription());
            stmt.setDouble(3, membership.getMembershipCost());
            stmt.setInt(4, membership.getMemberId());

            int rows = stmt.executeUpdate();
            AppLogger.getLogger().info("Membership added for user ID: " + membership.getMemberId());
            return rows > 0;

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error adding membership: " + e.getMessage());
            return false;
        }
    }

    // Get all memberships for a specific user
    public List<Membership> getMembershipsByUserId(int userId) {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE memberId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                memberships.add(mapRow(rs));
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error fetching memberships: " + e.getMessage());
        }
        return memberships;
    }

    // Get ALL memberships (Admin use)
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                memberships.add(mapRow(rs));
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error fetching all memberships: " + e.getMessage());
        }
        return memberships;
    }

    // Get total revenue from all memberships
    public double getTotalRevenue() {
        String sql = "SELECT SUM(membershipCost) AS total FROM memberships";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            AppLogger.getLogger().severe("Error calculating revenue: " + e.getMessage());
        }
        return 0.0;
    }

    private Membership mapRow(ResultSet rs) throws SQLException {
        return new Membership(
                rs.getInt("membershipId"),
                rs.getString("membershipType"),
                rs.getString("membershipDescription"),
                rs.getDouble("membershipCost"),
                rs.getInt("memberId")
        );
    }
}
