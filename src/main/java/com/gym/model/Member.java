package com.gym.model;

public class Member extends User {

    public Member() {
        super();
        setUserRole("Member");
    }

    public Member(int userId, String userName, String password, String email,
                  String phoneNumber, String address) {
        super(userId, userName, password, email, phoneNumber, address, "Member");
    }

    @Override
    public String toString() {
        return "[MEMBER] " + super.toString();
    }
}
