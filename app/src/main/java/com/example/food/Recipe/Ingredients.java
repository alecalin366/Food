package com.example.food.Recipe;

public class Ingredients {
    String name_ingredient;
    String quantity;
    String measurements;

    public Ingredients(){}

    public Ingredients(String name_ingredient, String quantity, String measurements){
        this.name_ingredient = name_ingredient;
        this.quantity = quantity;
        this.measurements = measurements;
    }

    public String getName_ingredient() {
        return name_ingredient;
    }

    public void setName_ingredient(String name_ingredient) {
        this.name_ingredient = name_ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }
}
