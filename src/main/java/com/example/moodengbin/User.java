package com.example.moodengbin;

class User {
    private String phone;
    private double cash;

    public User(String phone, double cash) {
        this.phone = phone;
        this.cash = cash;
    }

    public String getPhone() {
        return phone;
    }

    public double getCash() {
        return cash;
    }
}
