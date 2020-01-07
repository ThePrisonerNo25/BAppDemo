package com.example.yangchaoming.bappdemo.expand_recyclerview;


import com.example.yangchaoming.bappdemo.widget.expandable_recyclerview.Parent;

import java.util.List;

public class Recipe implements Parent<Ingredient> {

    private String mName;
    private List<Ingredient> mIngredients;

    public Recipe(String name, List<Ingredient> ingredients) {
        mName = name;
        mIngredients = ingredients;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<Ingredient> getChildList() {
        return mIngredients;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }

    public Ingredient getIngredient(int position) {
        return mIngredients.get(position);
    }

    public boolean isVegetarian() {
        for (Ingredient ingredient : mIngredients) {
            if (!ingredient.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
}
