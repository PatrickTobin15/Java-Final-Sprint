package com.gym.model;

public class GymMerch {

    private int merchId;
    private String merchName;
    private String merchType;
    private double merchPrice;
    private int quantityInStock;

    public GymMerch() {}

    public GymMerch(int merchId, String merchName, String merchType,
                    double merchPrice, int quantityInStock) {
        this.merchId = merchId;
        this.merchName = merchName;
        this.merchType = merchType;
        this.merchPrice = merchPrice;
        this.quantityInStock = quantityInStock;
    }

    // Getters
    public int getMerchId()          { return merchId; }
    public String getMerchName()     { return merchName; }
    public String getMerchType()     { return merchType; }
    public double getMerchPrice()    { return merchPrice; }
    public int getQuantityInStock()  { return quantityInStock; }

    // Setters
    public void setMerchId(int id)              { this.merchId = id; }
    public void setMerchName(String name)       { this.merchName = name; }
    public void setMerchType(String type)       { this.merchType = type; }
    public void setMerchPrice(double price)     { this.merchPrice = price; }
    public void setQuantityInStock(int qty)     { this.quantityInStock = qty; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) | Price: $%.2f | In Stock: %d",
                merchId, merchName, merchType, merchPrice, quantityInStock);
    }
}
