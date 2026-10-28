package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.VH> {

    private List<Ingredient> list = new ArrayList<>();
    private final MainActivity activity;
    private final DatabaseHelper db;

    public IngredientAdapter(MainActivity activity, DatabaseHelper db) {
        this.activity = activity;
        this.db = db;
    }

    public void setItems(List<Ingredient> newList) {
        this.list = newList;
        notifyDataSetChanged(); // Safe to ignore the yellow warning here
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Ingredient item = list.get(position);

        holder.name.setText(item.name);
        // Fixed warning: Using String.format instead of concatenation
        holder.qty.setText(String.format("%s %s", item.quantity, item.unit));

        // UPDATE (Edit)
        holder.btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(activity, AddEditActivity.class);
            i.putExtra("ingredient_id", item.id);
            activity.startActivity(i);
        });

        // DELETE
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Delete Ingredient")
                    .setMessage("Remove " + item.name + " from your pantry?")
                    // Fixed warning: Single line lambda format
                    .setPositiveButton("Yes", (d, w) -> { db.deleteIngredient(item.id); activity.refreshList(); })
                    .setNegativeButton("No", null).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Fixed warning: Made 'VH' public so it isn't exposed outside its scope
    public static class VH extends RecyclerView.ViewHolder {
        TextView name, qty, btnEdit, btnDelete;

        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.tv_name);
            qty = v.findViewById(R.id.tv_qty);
            btnEdit = v.findViewById(R.id.btn_edit);
            btnDelete = v.findViewById(R.id.btn_delete);
        }
    }
}