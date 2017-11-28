package com.dan.bakingapp.UI.DetailRecipe.DetailStep;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.Models.Step;
import com.dan.bakingapp.R;
import com.dan.bakingapp.UI.MainActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//from exo video

/**
 * Created by Dat T Do on 9/28/2017.
 */

public class StepFragment extends Fragment {

    @Bind(R.id.empty_image)
    ImageView emptyImage;
    @Bind(R.id.player_view)
    SimpleExoPlayerView mPlayerView;
    @Bind(R.id.step_description)
    TextView stepDescription;
    @Bind(R.id.nextStep)
    Button nextButton;
    @Bind(R.id.previousStep)
    Button previousButton;
    private Step mSelectedStep;
    private Recipe mSelectedRecipe;
    private SimpleExoPlayer mExoPlayer;
    //from exovideo
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private List<Step> allSteps;
    private ListItemClickListener itemClickListener;

    public StepFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        //from exo video
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        if (savedInstanceState == null) {
            //self-created
            mSelectedStep = getActivity().getIntent().getParcelableExtra(MainActivity.IntentKeyWord.SELECTED_STEP);
            mSelectedRecipe = getActivity().getIntent().getParcelableExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE);
        } else {
            mSelectedStep = savedInstanceState.getParcelable(MainActivity.IntentKeyWord.SELECTED_STEP);
            mSelectedRecipe = savedInstanceState.getParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE);
        }


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mSelectedStep.getShortDescription());

        if (mSelectedRecipe.getSteps() != null) {
            allSteps = mSelectedRecipe.getSteps();
        }

        Log.i("abcdef video", mSelectedStep.getVideoURL());
        Log.i("abcdef thumbnail", mSelectedStep.getThumbnailURL());

        if (!mSelectedStep.getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.GONE);
            Uri videoURI = Uri.parse(mSelectedStep.getVideoURL());
            initializePlayer(videoURI, mPlayerView);
        } else if (!mSelectedStep.getThumbnailURL().isEmpty()) {
            emptyImage.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            Uri thumbnailUri = Uri.parse(mSelectedStep.getThumbnailURL());
            initializePlayer(thumbnailUri, mPlayerView);
        } else {
            mPlayerView.setVisibility(View.GONE);
            emptyImage.setVisibility(View.VISIBLE);
        }

        stepDescription.setText(mSelectedStep.getDescription());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedStep.getId() + 1 < allSteps.size()) {
                    int nextStepIndex = mSelectedStep.getId() + 1;
                    Step nextStep = allSteps.get(nextStepIndex);
                    if (mExoPlayer != null) {
                        releasePlayer();
                    }
                    final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
                    if (!tabletSize) {
                        Intent intent = new Intent(getActivity(), StepActivity.class);
                        intent.putExtra(MainActivity.IntentKeyWord.SELECTED_STEP, nextStep);
                        intent.putExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE, mSelectedRecipe);
                        getActivity().finish();
                        startActivity(intent);
                    } else {
                        Bundle nextStepBundle = new Bundle();
                        nextStepBundle.putParcelable(MainActivity.IntentKeyWord.SELECTED_STEP, nextStep);
                        nextStepBundle.putParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE, mSelectedRecipe);
                        StepFragment nextStepFragment = new StepFragment();
                        nextStepFragment.setArguments(nextStepBundle);
                        getActivity().getFragmentManager().beginTransaction()
                                .replace(R.id.step_fragment, nextStepFragment).commitNow();
                    }
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedStep.getId() > 0) {
                    int previousStepIndex = mSelectedStep.getId() - 1;
                    Step previousStep = allSteps.get(previousStepIndex);
                    if (mExoPlayer != null) {
                        releasePlayer();
                    }
                    final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
                    if (tabletSize) {
                        Intent intent = new Intent(getActivity(), StepActivity.class);
                        intent.putExtra(MainActivity.IntentKeyWord.SELECTED_STEP, previousStep);
                        intent.putExtra(MainActivity.IntentKeyWord.SELECTED_RECIPE, mSelectedRecipe);
                        getActivity().finish();
                        startActivity(intent);
                    } else {
                        Bundle previousStepBundle = new Bundle();
                        previousStepBundle.putParcelable(MainActivity.IntentKeyWord.SELECTED_STEP, previousStep);
                        previousStepBundle.putParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE, mSelectedRecipe);
                        StepFragment previousStepFragment = new StepFragment();
                        previousStepFragment.setArguments(previousStepBundle);
                        getActivity().getFragmentManager().beginTransaction()
                                .replace(R.id.step_fragment, previousStepFragment).commitNow();
                    }
                }
            }
        });
        return rootView;
    }

    private void initializePlayer(Uri mediaUri, SimpleExoPlayerView simpleExoPlayerView) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer != null) {
            releasePlayer();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MainActivity.IntentKeyWord.SELECTED_RECIPE, mSelectedRecipe);
        outState.putParcelable(MainActivity.IntentKeyWord.SELECTED_STEP, mSelectedStep);
    }

    public interface ListItemClickListener {
        void onListItemClick(List<Step> allSteps, int Index, String recipeName);
    }
}


