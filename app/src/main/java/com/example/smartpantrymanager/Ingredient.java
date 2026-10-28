package com.example.smartpantrymanager;


// Simple data holder for one pantry ingredient
public class Ingredient {
    public int id;
    public String name;
    public double quantity;
    public String unit;

    public Ingredient(int id, String name, double quantity, String unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Example: "200 g" (removes the ".0" from whole numbers)
    public String getAmount() {
        String q = (quantity == (long) quantity) ? String.valueOf((long) quantity) : String.valueOf(quantity);
        return q + " " + unit;
    }
}