package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "pantry.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Normalized Database Structure
        db.execSQL("CREATE TABLE ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity REAL, unit TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, steps TEXT)");
        db.execSQL("CREATE TABLE recipe_ingredients (recipe_id INTEGER, name TEXT, quantity REAL, unit TEXT)");

        // Seed 15 Recipes
        addSeedRecipe(db, "Scrambled Eggs", "1. Crack eggs. 2. Fry in butter. 3. Add salt.", "eggs:2:pcs|butter:10:g|salt:1:tsp");
        addSeedRecipe(db, "Tomato Pasta", "1. Boil pasta. 2. Chop tomatoes. 3. Mix with oil.", "pasta:200:g|tomato:3:pcs|olive oil:2:tbsp");
        addSeedRecipe(db, "Cheese Toastie", "1. Butter bread. 2. Add cheese. 3. Toast.", "bread:2:pcs|cheese:50:g|butter:10:g");
        addSeedRecipe(db, "Fried Rice", "1. Cook rice. 2. Scramble eggs. 3. Fry together.", "rice:200:g|eggs:2:pcs|soy sauce:2:tbsp|oil:1:tbsp");
        addSeedRecipe(db, "Garlic Bread", "1. Mix butter and garlic. 2. Spread on bread. 3. Bake.", "bread:4:pcs|butter:50:g|garlic:2:pcs");
        addSeedRecipe(db, "Pancakes", "1. Mix ingredients. 2. Fry in pan.", "flour:200:g|milk:300:ml|eggs:2:pcs");
        addSeedRecipe(db, "Tomato Toast", "1. Toast bread. 2. Top with sliced tomato and salt.", "bread:2:pcs|tomato:1:pcs|salt:1:tsp");
        addSeedRecipe(db, "Banana Smoothie", "1. Blend all ingredients.", "banana:2:pcs|milk:200:ml|honey:1:tbsp");
        addSeedRecipe(db, "Grilled Cheese", "1. Put cheese on bread. 2. Grill.", "bread:2:pcs|cheese:60:g");
        addSeedRecipe(db, "Mashed Potatoes", "1. Boil potatoes. 2. Mash with butter and milk.", "potato:500:g|butter:50:g|milk:100:ml");
        addSeedRecipe(db, "Egg Salad", "1. Boil eggs. 2. Mix with mayo.", "eggs:3:pcs|mayonnaise:2:tbsp");
        addSeedRecipe(db, "Cheese Pasta", "1. Cook pasta. 2. Melt cheese over it.", "pasta:200:g|cheese:100:g");
        addSeedRecipe(db, "Oatmeal", "1. Cook oats in milk. 2. Add sliced banana.", "oats:100:g|milk:300:ml|banana:1:pcs");
        addSeedRecipe(db, "French Toast", "1. Beat eggs and milk. 2. Dip bread. 3. Fry.", "bread:4:pcs|eggs:2:pcs|milk:100:ml");
        addSeedRecipe(db, "Tomato Soup", "1. Boil tomatoes and onion. 2. Blend until smooth.", "tomato:5:pcs|onion:1:pcs|salt:1:tsp");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        onCreate(db);
    }

    // ================= CRUD: CREATE, READ, UPDATE, DELETE =================

    public void addIngredient(String name, double qty, String unit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name.toLowerCase().trim());
        cv.put("quantity", qty);
        cv.put("unit", unit.toLowerCase().trim());
        db.insert("ingredients", null, cv);
        db.close(); // Persists to disk
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ingredients ORDER BY name ASC", null);
        if (c.moveToFirst()) {
            do { list.add(new Ingredient(c.getInt(0), c.getString(1), c.getDouble(2), c.getString(3))); }
            while (c.moveToNext());
        }
        c.close(); db.close();
        return list;
    }

    public Ingredient getIngredient(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ingredients WHERE id=?", new String[]{String.valueOf(id)});
        Ingredient item = null;
        if (c.moveToFirst()) item = new Ingredient(c.getInt(0), c.getString(1), c.getDouble(2), c.getString(3));
        c.close(); db.close();
        return item;
    }

    public void updateIngredient(int id, String name, double qty, String unit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name.toLowerCase().trim());
        cv.put("quantity", qty);
        cv.put("unit", unit.toLowerCase().trim());
        db.update("ingredients", cv, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteIngredient(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("ingredients", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ================= RECIPE RETRIEVAL =================

    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM recipes", null);
        if (c.moveToFirst()) {
            do { list.add(new Recipe(c.getInt(0), c.getString(1), c.getString(2))); }
            while (c.moveToNext());
        }
        c.close(); db.close();
        return list;
    }

    public List<Ingredient> getRecipeIngredients(int recipeId) {
        List<Ingredient> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM recipe_ingredients WHERE recipe_id=?", new String[]{String.valueOf(recipeId)});
        if (c.moveToFirst()) {
            do { list.add(new Ingredient(0, c.getString(1), c.getDouble(2), c.getString(3))); }
            while (c.moveToNext());
        }
        c.close(); db.close();
        return list;
    }

    public Recipe getRecipe(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM recipes WHERE id=?", new String[]{String.valueOf(id)});
        Recipe r = null;
        if (c.moveToFirst()) r = new Recipe(c.getInt(0), c.getString(1), c.getString(2));
        c.close(); db.close();
        return r;
    }

    private void addSeedRecipe(SQLiteDatabase db, String name, String steps, String ings) {
        ContentValues rv = new ContentValues();
        rv.put("name", name); rv.put("steps", steps);
        long id = db.insert("recipes", null, rv);

        for (String s : ings.split("\\|")) {
            String[] p = s.split(":");
            ContentValues iv = new ContentValues();
            iv.put("recipe_id", id); iv.put("name", p[0]);
            iv.put("quantity", Double.parseDouble(p[1])); iv.put("unit", p[2]);
            db.insert("recipe_ingredients", null, iv);
        }
    }
}