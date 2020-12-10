package com.example.msifoodz;

public class Cart_food_item_list {
    String food_name;
    long food_price;
    int quantity;

    public Cart_food_item_list() {
    }

    public Cart_food_item_list(String food_name, long food_price, int quantity) {
        this.food_name = food_name;
        this.food_price = food_price;
        this.quantity = quantity;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public long getFood_price() {
        return food_price;
    }

    public void setFood_price(long food_price) {
        this.food_price = food_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
