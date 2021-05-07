package com.example.food.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.R;
import com.example.food.Recipe.DetailedRecipe;
import com.example.food.Recipe.Recipe;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class RecipeRecyclerViewAdapter extends FirestoreRecyclerAdapter<Recipe, RecipeRecyclerViewAdapter.RecipeViewHolder>{

    private Context mContext;
    public RecipeRecyclerViewAdapter(Context mContext, @NonNull FirestoreRecyclerOptions<Recipe> options) {
        super(options);
        this.mContext = mContext;;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_cardview, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull Recipe model) {
           holder.titlu_recipe.setText(model.getName());
           holder.date.setText(new Date(model.getMiliseconds()).toString());

        if(!model.photo.isEmpty())
        {
            Picasso.get()
                    .load(model.photo)
                    .placeholder(R.drawable.ic_android)
                    .error(R.drawable.ic_android)
                    .into(holder.recipe_photo);
        }

        holder.recipe_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailedRecipe.class);
                intent.putExtra("recipe",new Gson().toJson(model));
                mContext.startActivity(intent);
            }
        });

    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private ImageView recipe_photo;
        private TextView titlu_recipe, date;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_photo = itemView.findViewById(R.id.recipe_photo);
            titlu_recipe = itemView.findViewById(R.id.titlu_recipe);
            date = itemView.findViewById(R.id.date);
        }
    }

}
