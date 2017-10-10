package com.dan.bakingapp.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentActivity;

import com.dan.bakingapp.IdlingResource.SimpleIdlingResource;
import com.dan.bakingapp.R;

//import android.support.test.espresso.IdlingResource;
//import android.support.test.espresso.core.deps.guava.annotations.VisibleForTesting;
//import com.dan.bakingapp.IdlingResource.SimpleIdlingResource;


public class MainActivity extends FragmentActivity {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the IdlingResource instance
        getIdlingResource();
        // Create the main fragment and add it to the activity
        // using a fragment transaction.
        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .commit();

    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class IntentKeyWord {
        public static final String ALL_RECIPES = "All_Recipes";
        public static final String SELECTED_RECIPE = "Selected_Recipe";
        public static final String SELECTED_STEP = "Selected_Step";
        public static final String SELECTED_STEP_INDEX = "Selected_Index";
    }
}
