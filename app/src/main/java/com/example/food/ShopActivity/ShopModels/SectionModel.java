package com.example.food.ShopActivity.ShopModels;

import com.example.food.Recipe.Recipe;

public class SectionModel implements IShopModel {
    private Recipe recipe;

    public SectionModel(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
