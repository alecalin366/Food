package com.example.food.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public String name, category, description, photo;
    public String preparationTime, servingSize;
    Macronutrient macro;
    List<Ingredients> ingredients = new ArrayList<>();

    public Recipe(){}

    public Recipe(String name, String category, String description, String preparationTime, String servingSize,String photo, Macronutrient macro,List<Ingredients> ingredients){
        this.name=name;
        this.category = category;
        this.description=description;
        this.preparationTime = preparationTime;
        this.servingSize = servingSize;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Macronutrient getMacro() {
        return macro;
    }

    public void setMacro(Macronutrient macro) {
        this.macro = macro;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients>  ingredients) {
        this.ingredients = ingredients;
    }
}
