package com.dan.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dan.bakingapp.R;


public class MainActivity extends FragmentActivity {

    static String ALL_RECIPES = "All_Recipes";
    static String SELECTED_RECIPE = "Selected_Recipe";
    static String SELECTED_STEP = "Selected_Step";
    static String SELECTED_STEP_INDEX = "Selected_Index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Create the main fragment and add it to the activity
        // using a fragment transaction.
        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
