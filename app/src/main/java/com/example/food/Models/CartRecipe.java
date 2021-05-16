package com.example.food.Models;

public class CartRecipe {
    private String _recipeId;
    public CartRecipe(){}
    public CartRecipe(String _recipeId){
        this._recipeId = _recipeId;
    }

    public String get_recipeId() {
        return _recipeId;
    }

    public void set_recipeId(String _recipeId) {
        this._recipeId = _recipeId;
    }
}
