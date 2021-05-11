package com.example.food.Models;

public class LikeDislikeModel {
    private String UserID;

    public LikeDislikeModel()
    {
    }

    public LikeDislikeModel(String UserID)
    {
        this.UserID = UserID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
