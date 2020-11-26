package com.example.msifoodz;

public class food_item_model {
    private String f_name,f_category;
    private int f_price;

    public food_item_model() {
    }

    public food_item_model(String f_name, String f_category, int f_price) {
        this.f_name = f_name;
        this.f_category = f_category;
        this.f_price = f_price;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_category() {
        return f_category;
    }

    public void setF_category(String f_category) {
        this.f_category = f_category;
    }

    public int getF_price() {
        return f_price;
    }

    public void setF_price(int f_price) {
        this.f_price = f_price;
    }
}
