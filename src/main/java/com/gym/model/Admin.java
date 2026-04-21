package com.gym.model;

public class Admin extends User {

    public Admin() {
        super();
        setUserRole("Admin");
    }

    public Admin(int userId, String userName, String password, String email,
                 String phoneNumber, String address) {
        super(userId, userName, password, email, phoneNumber, address, "Admin");
    }

    @Override
    public String toString() {
        return "[ADMIN] " + super.toString();
    }
}
