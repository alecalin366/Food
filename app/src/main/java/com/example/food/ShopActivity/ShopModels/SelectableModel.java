package com.example.food.ShopActivity.ShopModels;

import com.example.food.Models.SpecialIngredient;

public class SelectableModel implements IShopModel {
    private SpecialIngredient specialIngredient;

    public SpecialIngredient getSpecialIngredient() {
        return specialIngredient;
    }

    public void setSpecialIngredient(SpecialIngredient specialIngredient) {
        this.specialIngredient = specialIngredient;
    }

    public SelectableModel(SpecialIngredient specialIngredient) {
        this.specialIngredient = specialIngredient;
    }
}
