package com.dan.bakingapp.UI.DetailRecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dan.bakingapp.R;
import com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientFragment;

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
                .replace(R.id.fragment_recipe, recipeFragment)
                .commitNow();
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (tabletSize) {
            IngredientFragment ingredientFragment = new IngredientFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_fragment, ingredientFragment)
                    .commitNow();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


