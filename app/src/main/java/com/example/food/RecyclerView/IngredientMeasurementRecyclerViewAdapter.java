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

public class IngredientMeasurementRecyclerViewAdapter extends RecyclerView.Adapter<IngredientMeasurementRecyclerViewAdapter.IngredientViewHolder> {

    List<Ingredients> mData;

    public IngredientMeasurementRecyclerViewAdapter(List<Ingredients> mData){
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
            holder.ingr_text.setText(mData.get(position).getMeasurements());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{
        private TextView ingr_text;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingr_text = itemView.findViewById(R.id.Ingredients_text);
        }
    }
}
