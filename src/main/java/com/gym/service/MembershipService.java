package com.gym.service;

import com.gym.dao.MembershipDAO;
import com.gym.model.Membership;
import com.gym.util.AppLogger;

import java.util.List;

public class MembershipService {

    private final MembershipDAO membershipDAO = new MembershipDAO();

    // Purchase a membership
    public boolean purchaseMembership(String type, String description, double cost, int userId) {
        Membership membership = new Membership(0, type, description, cost, userId);
        boolean success = membershipDAO.addMembership(membership);
        if (success) {
            AppLogger.getLogger().info("Membership purchased by user ID: " + userId + " | Type: " + type);
        }
        return success;
    }

    // Get memberships for a specific user
    public List<Membership> getMyMemberships(int userId) {
        return membershipDAO.getMembershipsByUserId(userId);
    }

    // Admin: get all memberships
    public List<Membership> getAllMemberships() {
        return membershipDAO.getAllMemberships();
    }

    // Admin: get total revenue
    public double getTotalRevenue() {
        return membershipDAO.getTotalRevenue();
    }
}
