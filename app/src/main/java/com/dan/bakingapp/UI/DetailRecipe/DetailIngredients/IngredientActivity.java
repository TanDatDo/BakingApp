package com.dan.bakingapp.UI.DetailRecipe.DetailIngredients;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dan.bakingapp.R;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        // Create the detail fragment and add it to the activity
        // using a fragment transaction.
        IngredientFragment ingredientFragment = new IngredientFragment();

        // Create the main fragment and add it to the activity
        // using a fragment transaction.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_ingredient_fragment, ingredientFragment)
                .commitNow();

    }
}
