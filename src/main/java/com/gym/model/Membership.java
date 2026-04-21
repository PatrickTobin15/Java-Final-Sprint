package com.gym.model;

public class Membership {

    private int membershipId;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberId;

    public Membership() {}

    public Membership(int membershipId, String membershipType, String membershipDescription,
                      double membershipCost, int memberId) {
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberId = memberId;
    }

    // Getters
    public int getMembershipId()             { return membershipId; }
    public String getMembershipType()        { return membershipType; }
    public String getMembershipDescription() { return membershipDescription; }
    public double getMembershipCost()        { return membershipCost; }
    public int getMemberId()                 { return memberId; }

    // Setters
    public void setMembershipId(int id)                    { this.membershipId = id; }
    public void setMembershipType(String type)             { this.membershipType = type; }
    public void setMembershipDescription(String desc)      { this.membershipDescription = desc; }
    public void setMembershipCost(double cost)             { this.membershipCost = cost; }
    public void setMemberId(int memberId)                  { this.memberId = memberId; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s | Cost: $%.2f | User ID: %d",
                membershipId, membershipType, membershipDescription, membershipCost, memberId);
    }
}
