package com.dan.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.R;

public class DetailRecipeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe_activity);



            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            DetailRecipeFragment detailFragment = new DetailRecipeFragment();

            // Create the main fragment and add it to the activity
            // using a fragment transaction.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_recipe_fragment, detailFragment)
                    .commitNow();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


