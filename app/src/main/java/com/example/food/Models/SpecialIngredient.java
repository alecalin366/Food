package com.example.food.Models;

import com.example.food.Recipe.Ingredients;

public class SpecialIngredient {
    private boolean _isChecked;
    private Ingredients _ingredient;
    private String _recipeId;

    public boolean is_isChecked() {
        return _isChecked;
    }

    public SpecialIngredient(){}

    public String get_recipeId() {
        return _recipeId;
    }

    public void set_recipeId(String _recipeId) {
        this._recipeId = _recipeId;
    }

    public SpecialIngredient(boolean _isChecked, String _recipeId, Ingredients _ingredient) {
        this._isChecked = _isChecked;
        this._ingredient = _ingredient;
        this._recipeId = _recipeId;
    }

    public void set_isChecked(boolean _isChecked) {
        this._isChecked = _isChecked;
    }

    public Ingredients get_ingredient() {
        return _ingredient;
    }

    public void set_ingredient(Ingredients _ingredient) {
        this._ingredient = _ingredient;
    }
}
