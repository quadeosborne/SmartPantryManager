package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecipeAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        db = new DatabaseHelper(this);
        emptyText = findViewById(R.id.tv_empty_recipes);
        RecyclerView recycler = findViewById(R.id.recycler_recipes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecipeAdapter(this);
        recycler.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_recipes);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_pantry) { startActivity(new Intent(this, MainActivity.class)); finish(); return true; }
            if (id == R.id.nav_settings) { startActivity(new Intent(this, SettingsActivity.class)); finish(); return true; }
            return id == R.id.nav_recipes;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Recipe> allRecipes = db.getAllRecipes();
        List<Ingredient> pantry = db.getAllIngredients();
        List<Recipe> matches = new ArrayList<>();

        // Apply STRICT MATCHING
        for (Recipe r : allRecipes) {
            if (RecipeMatcher.canMakeRecipe(db.getRecipeIngredients(r.id), pantry)) {
                matches.add(r);
            }
        }

        adapter.setItems(matches);
        emptyText.setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
        findViewById(R.id.recycler_recipes).setVisibility(matches.isEmpty() ? View.GONE : View.VISIBLE);
    }
}