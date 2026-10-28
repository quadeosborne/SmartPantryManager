package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        int id = getIntent().getIntExtra("recipe_id", -1);
        DatabaseHelper db = new DatabaseHelper(this);
        Recipe r = db.getRecipe(id);

        if (r != null) {
            ((TextView) findViewById(R.id.tv_title)).setText(r.name);
            ((TextView) findViewById(R.id.tv_steps)).setText(r.steps);
        }
    }
}