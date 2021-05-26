package com.example.food.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.example.food.Recipe.DetailedRecipe;
import com.example.food.Recipe.Recipe;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder>{

    private Context mContext;
    public ArrayList<Recipe>  RecipeList;

    public RecipeRecyclerViewAdapter(Context mContext, ArrayList<Recipe> list)
    {
        this.mContext = mContext;
        RecipeList = list;
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_cardview, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe model = RecipeList.get(position);
        holder.titlu_recipe.setText(model.getName());
        holder.date.setText(new Date(model.getMiliseconds()).toString());
        holder.time.setText(model.getPreparationTime());
        holder.likes.setText(String.valueOf(model.getLikesCount()));
        holder.dislikes.setText(String.valueOf(model.getDislikesCount()));

        if(!model.photo.isEmpty())
        {
            Picasso.get()
                    .load(model.photo)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .into(holder.recipe_photo);
        }

        holder.recipe_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailedRecipe.class);
                intent.putExtra("recipe",new Gson().toJson(model));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return RecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private ImageView recipe_photo;
        private TextView titlu_recipe, date;
        //        private CircleImageView ownerPhoto;
//        private TextView ownerName;
        private TextView time;
        private TextView likes, dislikes;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_photo = itemView.findViewById(R.id.recipe_photo);
            titlu_recipe = itemView.findViewById(R.id.titlu_recipe);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.detailed_recipe_time);
            likes = itemView.findViewById(R.id.likes);
            dislikes = itemView.findViewById(R.id.dislikes);
        }
    }
}
