package com.example.food.User;

public class UserSettings {

    private User userSettings;
    //private UserAccountSettings settings;

    public UserSettings(User userSettings) {
        this.userSettings = userSettings;
    }

    public UserSettings(){}

    public User getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(User userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + userSettings +
                '}';
    }
}
