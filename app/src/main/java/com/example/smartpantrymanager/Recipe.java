package com.example.smartpantrymanager;

// Simple data holder for one recipe
public class Recipe {
    public int id;
    public String name;
    public String steps;

    public Recipe(int id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }
}