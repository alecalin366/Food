package com.example.food.Recipe;

public class Macronutrient {
    String calorii, proteine, carbo, grasimi ;

    public Macronutrient(){}

    public Macronutrient(String calorii, String proteine, String carbo, String grasimi){
        this.calorii = calorii;
        this.proteine = proteine;
        this.carbo = carbo;
        this.grasimi = grasimi;
    }

    public String getCalorii() {
        return calorii;
    }

    public void setCalorii(String calorii) {
        this.calorii = calorii;
    }

    public String getProteine() {
        return proteine;
    }

    public void setProteine(String proteine) {
        this.proteine = proteine;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public String getGrasimi() {
        return grasimi;
    }

    public void setGrasimi(String grasimi) {
        this.grasimi = grasimi;
    }
}
