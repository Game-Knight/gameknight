package com.cs_356.app.Views.GameNightView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.Views.GameViewActivity;
import com.cs_356.app.databinding.FragmentAvailableGamesBinding;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.BoardGame;
import Entities.GameNight;

public class AvailableGamesFragment extends Fragment implements GameCardAdapter.OnGameCardClickListener {

    private FragmentAvailableGamesBinding binding;
    private static GameNight gameNight;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentAvailableGamesBinding.inflate(inflater, container, false);

        gameNight = (GameNight) requireActivity().getIntent().getSerializableExtra(Constants.GAME_NIGHT_KEY);

        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) binding.progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        loadGamesInBackground(this, requireActivity());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onGameCardClick(int position) {
        Intent intent = new Intent(requireActivity(), GameViewActivity.class);
        intent.putExtra(
                Constants.GAME_KEY,
                FrontendCache.getGamesAvailableForGameNight(gameNight.getId()).get(position)
        );
        startActivity(intent);
    }

    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    Handler mHandler = new Handler(Looper.getMainLooper());

    // Create an interface to respond with the result after processing
    public interface OnProcessedListener {
        void onProcessed(boolean success);
    }

    private void loadGamesInBackground(GameCardAdapter.OnGameCardClickListener cardClickListener, Context context) {

        final OnProcessedListener listener = new OnProcessedListener() {
            @Override
            public void onProcessed(boolean success) {
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressSpinner.setVisibility(View.GONE);
                        GameCardAdapter adapter = new GameCardAdapter(
                                FrontendCache.getGamesAvailableForGameNight(gameNight.getId()),
                                cardClickListener,
                                context
                        );
                        binding.gameNightRecyclerView.setAdapter(adapter);
                        mExecutor.shutdown();
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable() {
            @Override
            public void run() {
                binding.progressSpinner.setVisibility(View.VISIBLE);
                FrontendCache.getGamesForAuthenticatedUser().sort(Comparator.comparing(BoardGame::getName));

                listener.onProcessed(true);
            }
        };

        mExecutor.execute(backgroundRunnable);
    }
}