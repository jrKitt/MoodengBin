package com.example.moodengbin;

public class Menu {
    private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String imagePath;

    public Menu(int id, String name, String category, double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public String getImagePath() {
        return imagePath;
    }
}
