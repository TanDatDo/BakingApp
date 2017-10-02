package com.dan.bakingapp.UI;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.R;

public class DetailStepActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        // Create the main fragment and add it to the activity
        // using a fragment transaction.
        DetailStepFragment detailStepFragment = new DetailStepFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.detail_step_fragment, detailStepFragment)
                .commit();
    }

}
