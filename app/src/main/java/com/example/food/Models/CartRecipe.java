package com.example.food.Models;

import com.example.food.Recipe.Recipe;

import java.util.ArrayList;

public class CartRecipe {
    public CartRecipe(){}

    private ArrayList<SpecialIngredient> ingredients;
    private Recipe recipe;

    public ArrayList<SpecialIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<SpecialIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public CartRecipe(Recipe recipe, ArrayList<SpecialIngredient> ingredients) {
        this.ingredients = ingredients;
        this.recipe = recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
