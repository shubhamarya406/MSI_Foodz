package com.example.msifoodz;

public class Dashboard_food_category_details {
    String foodCategory;
    int thumbnail;

    public Dashboard_food_category_details(String foodCategory, int thumbnail) {
        this.foodCategory = foodCategory;
        this.thumbnail = thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }
}
