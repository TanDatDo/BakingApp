package com.dan.bakingapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.bakingapp.Adapter.DetailAdapter;
import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.Models.Step;
import com.dan.bakingapp.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dat T Do on 9/28/2017.
 */

public class DetailRecipeFragment extends Fragment implements ExoPlayer.EventListener {
    public DetailRecipeFragment() {
    }

    @Bind(R.id.detail_recipe_recycler)
    RecyclerView mRecipeRecyclerView;
    String TAG= getClass().getSimpleName();


    private Recipe mRecipe;
    private List<Step> mStepList;
    private DetailAdapter detailAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.detail_recipe_fragment, container, false);
        ButterKnife.bind(this, rootView);
        mRecipe = getActivity().getIntent().getParcelableExtra("recipe");
        mStepList = mRecipe.getSteps();
        if (rootView.getTag() != null && rootView.getTag().equals("phone-land")) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            mRecipeRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecipeRecyclerView.setLayoutManager(mLayoutManager);
        }

        detailAdapter = new DetailAdapter(getContext(), (ArrayList<Step>) mStepList);
        detailAdapter.notifyDataSetChanged();
        mRecipeRecyclerView.setAdapter(detailAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipeRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Step currentStep = mStepList.get(position);
                Bundle arguments = new Bundle();
                int selectedStepIndex= position;
                arguments.putParcelable(MainActivity.SELECTED_RECIPE, mRecipe);
                arguments.putInt(MainActivity.SELECTED_STEP_INDEX, selectedStepIndex);
                arguments.putParcelable(MainActivity.SELECTED_STEP, currentStep);
                Intent intent = new Intent(getActivity(), DetailStepActivity.class);
                intent.putExtra("step", currentStep);
                startActivity(intent);
            }
        }));
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
}

