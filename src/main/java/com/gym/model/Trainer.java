package com.gym.model;

public class Trainer extends User {

    public Trainer() {
        super();
        setUserRole("Trainer");
    }

    public Trainer(int userId, String userName, String password, String email,
                   String phoneNumber, String address) {
        super(userId, userName, password, email, phoneNumber, address, "Trainer");
    }

    @Override
    public String toString() {
        return "[TRAINER] " + super.toString();
    }
}
