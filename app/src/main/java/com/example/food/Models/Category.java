package com.example.food.Models;

public class Category {
    private String name_category;
    private String pictureURL;

    private Category(){}

    private Category(String name_category, String pictureURL) {
        this.name_category = name_category;
        this.pictureURL = pictureURL;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

}
