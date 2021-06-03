package com.example.food.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.Recipe.Ingredients;

import java.util.List;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.IngredientViewHolder> {

    List<Ingredients> mData;

    public IngredientRecyclerViewAdapter(List<Ingredients> mData){
        this.mData = mData;
    }
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_measurements_layout, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.ingr_text.setText(mData.get(position).getName_ingredient());
        holder.ingr_value.setText(String.format("%s %s", mData.get(position).getQuantity(), mData.get(position).getMeasurements()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{
        private final TextView ingr_text;
        private final TextView ingr_value;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingr_text = itemView.findViewById(R.id.Ingredients_text);
            ingr_value = itemView.findViewById(R.id.Ingredients_value);
        }
    }
}
