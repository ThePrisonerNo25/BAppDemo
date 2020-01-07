package com.example.yangchaoming.bappdemo.expand_recyclerview;

public class Ingredient {

    private String mName;
    private boolean mIsVegetarian;

    public Ingredient(String name, boolean isVegetarian) {
        mName = name;
        mIsVegetarian = isVegetarian;
    }

    public String getName() {
        return mName;
    }

    public boolean isVegetarian() {
        return mIsVegetarian;
    }
}
