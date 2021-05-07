package com.example.food.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class DetailedRecipe extends AppCompatActivity {
    private Recipe recipe;
    Button favorite,cart;
    ImageView backArrow, photo;
    TextView name,ownerName, preparationTime, servings, description, macro_calorii, macro_proteine, macro_carbo, macro_grasimi;
    RecyclerView ingr_name, ingr_quntity, ingr_measure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        Intent intent = getIntent();
        recipe = new Gson().fromJson(intent.getStringExtra("recipe"),Recipe.class);

        FindViews();
        SetViewsFields();

    }

    private void FindViews()
    {
        favorite = findViewById(R.id.favoriteButton);
        cart = findViewById(R.id.addToCart);
        backArrow = findViewById(R.id.backArrow);
        photo = findViewById(R.id.detailed_recipe_photo);
        name = findViewById(R.id.detailed_recipe_title);
        ownerName = findViewById(R.id.owner);
        preparationTime = findViewById(R.id.detailed_recipe_time);
        servings = findViewById(R.id.portii);
        ingr_name = findViewById(R.id.recyclerView1);
        ingr_quntity = findViewById(R.id.recyclerView2);
        ingr_measure = findViewById(R.id.recyclerView3);
        description = findViewById(R.id.descriere);
        macro_calorii = findViewById(R.id.cantitate_calorii);
        macro_proteine = findViewById(R.id.cantitate_proteine);
        macro_carbo = findViewById(R.id.cantitate_carbo);
        macro_grasimi = findViewById(R.id.cantitate_grasimi);
    }

    private void SetViewsFields()
    {
        name.setText(recipe.getName());
        description.setText(recipe.getDescription());
        preparationTime.setText(recipe.getPreparationTime());
        servings.setText(recipe.getServingSize());

        if(!recipe.photo.isEmpty())
        {
            Picasso.get()
                    .load(recipe.photo)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .into(photo);
        }

//        String MacroCalorii = macro_calorii.getText().toString();
//        MacroCalorii = recipe.macro.calorii;
//        String MacroProteine = macro_proteine.getText().toString();
//        MacroCalorii = recipe.macro.proteine;
//        String MacroCarbo = macro_carbo.getText().toString();
//        MacroCarbo = recipe.macro.carbo;
//        String MacroGrasimi = macro_grasimi.getText().toString();
//        MacroCarbo = recipe.macro.grasimi;

    }

}
