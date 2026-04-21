package com.gym.model;

public class User {

    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String userRole; // "Admin", "Trainer", "Member"

    public User() {}

    public User(int userId, String userName, String password, String email,
                String phoneNumber, String address, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }

    // Getters
    public int getUserId()       { return userId; }
    public String getUserName()  { return userName; }
    public String getPassword()  { return password; }
    public String getEmail()     { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress()   { return address; }
    public String getUserRole()  { return userRole; }

    // Setters
    public void setUserId(int userId)           { this.userId = userId; }
    public void setUserName(String userName)    { this.userName = userName; }
    public void setPassword(String password)    { this.password = password; }
    public void setEmail(String email)          { this.email = email; }
    public void setPhoneNumber(String ph)       { this.phoneNumber = ph; }
    public void setAddress(String address)      { this.address = address; }
    public void setUserRole(String userRole)    { this.userRole = userRole; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) | Email: %s | Phone: %s | Address: %s",
                userId, userName, userRole, email, phoneNumber, address);
    }
}
