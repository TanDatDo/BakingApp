package com.dan.bakingapp.UI;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dan.bakingapp.Models.Recipe;
import com.dan.bakingapp.Models.Step;
import com.dan.bakingapp.R;
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

public class DetailStepFragment extends Fragment {

    private Step mSelectedStep;
    private int mSelectedStepIndex;
    private Recipe mSelectedRecipe;

    private SimpleExoPlayer mExoPlayer;
    @Bind(R.id.player_view)
    SimpleExoPlayerView mPlayerView;
    @Bind(R.id.step_description)
    TextView stepDescription;
    @Bind(R.id.nextStep)
    Button nextButton;
    @Bind(R.id.previousStep)
    Button previousButton;
    //from exovideo
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private List<Step> allSteps;

    private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Step> allSteps, int Index, String recipeName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.detail_step_fragment, container, false);
        ButterKnife.bind(this, rootView);
        //from exo video
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        //self-created
        mSelectedStep = getActivity().getIntent().getParcelableExtra(MainActivity.SELECTED_STEP);
        mSelectedStepIndex = getActivity().getIntent().getIntExtra(MainActivity.SELECTED_STEP_INDEX, 0);
        mSelectedRecipe = getActivity().getIntent().getParcelableExtra(MainActivity.SELECTED_RECIPE);
        allSteps = mSelectedRecipe.getSteps();

        if (mSelectedStep.getVideoURL() != "" && mSelectedStep.getThumbnailURL() != null) {
            Uri videoURI = Uri.parse(mSelectedStep.getVideoURL());
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(videoURI, mPlayerView);
        } else if (mSelectedStep.getThumbnailURL() != "" && mSelectedStep.getThumbnailURL() != null) {
            mPlayerView.setVisibility(View.VISIBLE);
            Uri thumbnailUri = Uri.parse(mSelectedStep.getThumbnailURL());
            initializePlayer(thumbnailUri, mPlayerView);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        stepDescription.setText(mSelectedStep.getDescription());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedStep.getId() + 1 < allSteps.size()) {
                    int nextStepIndex = mSelectedStepIndex + 1;
                    Step nextStep = allSteps.get(nextStepIndex);
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MainActivity.SELECTED_STEP, nextStep);
                    arguments.putInt(MainActivity.SELECTED_STEP_INDEX, nextStepIndex);
                    arguments.putParcelable(MainActivity.SELECTED_RECIPE, mSelectedRecipe);
                }


            }
        });


        return rootView;
    }

    private void initializePlayer(Uri mediaUri, SimpleExoPlayerView simpleExoPlayerView) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.

            //from exovideo
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
        mExoPlayer = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//       releasePlayer();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        releasePlayer();
//    }


    //
// Remember to stop the video when then navigation is clicked
// handler.postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            mExoPlayer.stop();
//            Intent nextQuestionIntent = new Intent(QuizActivity.this, QuizActivity.class);
//            nextQuestionIntent.putExtra(REMAINING_SONGS_KEY, mRemainingSampleIDs);
//            finish();
//            startActivity(nextQuestionIntent);
//        }
//    }, CORRECT_ANSWER_DELAY_MILLIS);
//}


}


