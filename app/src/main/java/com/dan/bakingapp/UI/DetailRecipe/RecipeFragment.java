package com.dan.bakingapp.UI.DetailRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dan.bakingapp.Models.Ingredient;
import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.Models.Step;
import com.dan.bakingapp.R;
import com.dan.bakingapp.UI.DetailRecipe.DetailIngredients.IngredientActivity;
import com.dan.bakingapp.UI.DetailRecipe.DetailStep.StepActivity;
import com.dan.bakingapp.UI.MainActivity;
import com.dan.bakingapp.UI.RecyclerClickListener;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dat T Do on 9/28/2017.
 */

public class RecipeFragment extends Fragment implements ExoPlayer.EventListener {
    @Bind(R.id.ingredient_card_view)
    CardView ingredientCardView;
    @Bind(R.id.ingredient_text_view)
    TextView ingredientTextView;
    @Bind(R.id.detail_recipe_recycler)
    RecyclerView mRecipeRecyclerView;
    String TAG= getClass().getSimpleName();
    private Recipe mRecipe;
    private List<Step> mStepList;
    private RecipeAdapter mRecipeAdapter;

    public RecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);
        if (savedInstanceState == null) {
            mRecipe = getActivity().getIntent().getParcelableExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE);
        } else {
            mRecipe = savedInstanceState.getParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mRecipe.getName());
        ingredientTextView.setText(getString(R.string.ingredient_of) + " " + mRecipe.getName());
        mStepList = mRecipe.getSteps();
        if (rootView.getTag() != null && rootView.getTag().equals("phone-land")) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            mRecipeRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecipeRecyclerView.setLayoutManager(mLayoutManager);
        }

        mRecipeAdapter = new RecipeAdapter(getContext(), (ArrayList<Step>) mStepList);
        mRecipeAdapter.notifyDataSetChanged();
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipeRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Step currentStep = mStepList.get(position);
                int selectedStepIndex= position;
                Intent intent = new Intent(getActivity(), StepActivity.class);
                intent.putExtra(MainActivity.IntentKeyWord.SELECTED_STEP, currentStep);
                intent.putExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE, mRecipe);
                intent.putExtra(MainActivity.IntentKeyWord.SELECTED_STEP_INDEX, selectedStepIndex);
                startActivity(intent);
            }
        }));

        ingredientCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Ingredient> currentIngredients = mRecipe.getIngredients();
                Intent intent = new Intent(getActivity(), IngredientActivity.class);
                intent.putExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE, mRecipe);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
                        Log.d(TAG, "onPlayerStateChanged: PLAYING");
                    } else if((playbackState == ExoPlayer.STATE_READY)){
                        Log.d(TAG, "onPlayerStateChanged: PAUSED");
                  }

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE, mRecipe);
    }
}

