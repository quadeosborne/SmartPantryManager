package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText etName, etQty, etUnit;
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        db = new DatabaseHelper(this);
        etName = findViewById(R.id.et_name);
        etQty = findViewById(R.id.et_qty);
        etUnit = findViewById(R.id.et_unit);
        Button btnSave = findViewById(R.id.btn_save);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);
        if (ingredientId != -1) {
            Ingredient item = db.getIngredient(ingredientId);
            if (item != null) {
                etName.setText(item.name);
                etQty.setText(String.valueOf(item.quantity));
                etUnit.setText(item.unit);
                setTitle("Edit Ingredient");
            }
        }

        btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        String name = etName.getText().toString().trim();
        String qtyStr = etQty.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();

        // Input Validation
        if (name.isEmpty()) { etName.setError("Required"); return; }
        if (qtyStr.isEmpty()) { etQty.setError("Required"); return; }
        if (unit.isEmpty()) { etUnit.setError("e.g. g, ml, pcs"); return; }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
            if (qty <= 0) { etQty.setError("Must be > 0"); return; }
        } catch (Exception e) { etQty.setError("Invalid Number"); return; }

        if (ingredientId == -1) {
            db.addIngredient(name, qty, unit); // CREATE
        } else {
            db.updateIngredient(ingredientId, name, qty, unit); // UPDATE
        }
        finish();
    }
}