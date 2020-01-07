package com.example.yangchaoming.bappdemo.expand_recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yangchaoming.bappdemo.R;
import com.example.yangchaoming.bappdemo.widget.expandable_recyclerview.ChildViewHolder;


public class IngredientViewHolder extends ChildViewHolder {

    private TextView mIngredientTextView;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);
    }

    public void bind(@NonNull Ingredient ingredient) {
        mIngredientTextView.setText(ingredient.getName());
    }
}
