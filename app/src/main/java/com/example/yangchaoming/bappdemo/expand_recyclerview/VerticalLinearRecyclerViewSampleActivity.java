package com.example.yangchaoming.bappdemo.expand_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.yangchaoming.bappdemo.R;
import com.example.yangchaoming.bappdemo.widget.expandable_recyclerview.ExpandableRecyclerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Sample Activity for the vertical linear RecyclerView sample.
 * Uses ButterKnife to inject view resources.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class VerticalLinearRecyclerViewSampleActivity extends AppCompatActivity {

    private RecipeAdapter mAdapter;

    @NonNull
    public static Intent newIntent(Context context) {
        return new Intent(context, VerticalLinearRecyclerViewSampleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_sample);

        Ingredient beef = new Ingredient("beef", false);
        Ingredient cheese = new Ingredient("cheese", true);
        Ingredient salsa = new Ingredient("salsa", true);
        Ingredient tortilla = new Ingredient("tortilla", true);
        Ingredient ketchup = new Ingredient("ketchup", true);
        Ingredient bun = new Ingredient("bun", true);

        Recipe taco = new Recipe("taco", Arrays.asList(beef, cheese, salsa, tortilla));
        Recipe quesadilla = new Recipe("quesadilla", Arrays.asList(cheese, tortilla));
        Recipe burger = new Recipe("burger", Arrays.asList(beef, cheese, ketchup, bun));
        final List<Recipe> recipes = Arrays.asList(taco, quesadilla, burger);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new RecipeAdapter(this, recipes);

        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {
                Recipe expandedRecipe = recipes.get(parentPosition);

                String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
                Toast.makeText(VerticalLinearRecyclerViewSampleActivity.this,
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @UiThread
            @Override
            public void onParentCollapsed(int parentPosition) {
                Recipe collapsedRecipe = recipes.get(parentPosition);

                String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                Toast.makeText(VerticalLinearRecyclerViewSampleActivity.this,
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }
}
