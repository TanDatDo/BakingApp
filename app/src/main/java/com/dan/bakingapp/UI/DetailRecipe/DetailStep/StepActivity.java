package com.dan.bakingapp.UI.DetailRecipe.DetailStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dan.bakingapp.R;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        // Create the main fragment and add it to the activity
        // using a fragment transaction.
        StepFragment stepFragment = new StepFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.detail_step_fragment, stepFragment)
                .commit();
    }

}
