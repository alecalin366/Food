package com.example.food.Recipe;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food.R;
import com.google.gson.Gson;


public class DetailedRecipe extends AppCompatActivity {
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        Intent intent = getIntent();
        recipe = new Gson().fromJson(intent.getStringExtra("recipe"),Recipe.class);

    }

    private void FindViews()
    {

    }

    private void SetViewsFields()
    {
        //title.text = recipe.nume
    }

}
