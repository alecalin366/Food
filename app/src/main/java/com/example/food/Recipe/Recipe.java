package com.example.food.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String user_id;
    public String name, category, description, photo, recipeId;
    public String preparationTime, servingSize;
    private int LikesCount,DislikesCount;

    public int getLikesCount() {
        return LikesCount;
    }

    public int getDislikesCount() {
        return DislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        DislikesCount = dislikesCount;
    }

    public void setLikesCount(int likesCount) {
        LikesCount = likesCount;
    }

    Macronutrient macro;
    List<Ingredients> ingredients = new ArrayList<>();
    long miliseconds;

    public Recipe() {
    }

    public Recipe(String user_id, String name, String category, String description, String preparationTime, String servingSize, String photo, String recipeId, Macronutrient macro, List<Ingredients> ingredients) {
        this.user_id = user_id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.preparationTime = preparationTime;
        this.servingSize = servingSize;
        this.photo = photo;
        this.macro = macro;
        this.ingredients = ingredients;
        this.miliseconds = System.currentTimeMillis();
        this.recipeId = recipeId;

    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
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

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getMiliseconds() {
        return miliseconds;
    }

    public void setMiliseconds(long miliseconds) {
        this.miliseconds = miliseconds;
    }
}
