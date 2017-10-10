package com.dan.bakingapp.UI.DetailRecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dan.bakingapp.R;

public class RecipeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Create the detail fragment and add it to the activity
        // using a fragment transaction.
        RecipeFragment recipeFragment = new RecipeFragment();

        // Create the main fragment and add it to the activity
        // using a fragment transaction.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_recipe_fragment, recipeFragment)
                .commitNow();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


