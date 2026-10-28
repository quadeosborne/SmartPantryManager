package com.example.smartpantrymanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {
    private List<Recipe> list = new ArrayList<>();
    private final RecipesActivity activity;

    public RecipeAdapter(RecipesActivity activity) { this.activity = activity; }

    public void setItems(List<Recipe> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Recipe r = list.get(position);
        holder.name.setText(r.name);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(activity, RecipeDetailActivity.class);
            i.putExtra("recipe_id", r.id);
            activity.startActivity(i);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView name;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.tv_recipe_name);
        }
    }
}