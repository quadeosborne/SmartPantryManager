package com.example.smartpantrymanager;

import java.util.List;

public class RecipeMatcher {

    // Handles plurals: "tomatoes" -> "tomato", "eggs" -> "egg"
    public static String cleanName(String text) {
        String s = text.toLowerCase().trim();
        if (s.endsWith("oes")) return s.substring(0, s.length() - 2);
        if (s.endsWith("ies")) return s.substring(0, s.length() - 3) + "y";
        if (s.endsWith("s") && !s.endsWith("ss")) return s.substring(0, s.length() - 1);
        return s;
    }

    // Converts weights/volumes to base units for accurate math
    public static double convertToBaseUnit(double quantity, String unit) {
        unit = unit.toLowerCase().trim();
        if (unit.equals("kg")) return quantity * 1000;
        if (unit.equals("l")) return quantity * 1000;
        return quantity; // g, ml, pcs remain as-is
    }

    // THE STRICT RULE: Returns true ONLY if ALL ingredients are present
    public static boolean canMakeRecipe(List<Ingredient> required, List<Ingredient> pantry) {
        for (Ingredient req : required) {
            String reqName = cleanName(req.name);
            double reqQty = convertToBaseUnit(req.quantity, req.unit);
            boolean foundEnough = false;

            for (Ingredient stock : pantry) {
                if (cleanName(stock.name).equals(reqName)) {
                    double stockQty = convertToBaseUnit(stock.quantity, stock.unit);
                    if (stockQty >= reqQty) {
                        foundEnough = true;
                        break;
                    }
                }
            }
            // If even one ingredient is missing, fail immediately
            if (!foundEnough) return false;
        }
        return true;
    }
}