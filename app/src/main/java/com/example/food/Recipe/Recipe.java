package com.example.food.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public String name, category, description;
    public String preparationTime, servingSize;
    Macronutrient macro;
    String ingredients;

    public Recipe(){}

    public Recipe(String name, String category, String description, String preparationTime, String servingSize, Macronutrient macro,String ingredients){
        this.name=name;
        this.category = category;
        this.description=description;
        this.preparationTime = preparationTime;
        this.servingSize = servingSize;
        this.macro = macro;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public Macronutrient getMacro() {
        return macro;
    }

    public void setMacro(Macronutrient macro) {
        this.macro = macro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String  ingredients) {
        this.ingredients = ingredients;
    }
}
