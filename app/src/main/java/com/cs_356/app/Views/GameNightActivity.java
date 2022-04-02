package com.cs_356.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.Utils.DateUtils;
import com.cs_356.app.databinding.ActivityGameLibraryBinding;
import com.cs_356.app.databinding.ActivityGameNightBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.BoardGame;
import Entities.GameNight;

/**
 * This displays a single game night. If you were the one who created
 * the game night, you'll be able to manage the invite list. This display
 * should show the user which games will be played. It should also allow the
 * user to vote for the games they want to play.
 * TODO: Add a fragment for managing the invite list.
 */
public class GameNightActivity extends AppCompatActivity implements GameCardAdapter.OnGameCardClickListener {

    private ActivityGameNightBinding binding;
    private static GameNight gameNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameNightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gameNight = (GameNight) getIntent().getSerializableExtra(Constants.GAME_NIGHT_KEY);

        binding.gameNightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.gameNightTitle.setText(gameNight.getName());
        binding.gameNightLocation.setText(gameNight.getLocation());
        binding.gameNightDateTime.setText(DateUtils.formatDate(gameNight.getDate()));

        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) binding.progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        loadGamesInBackground(this);
    }

    @Override
    public void onGameCardClick(int position) {
        Intent intent = new Intent(this, GameViewActivity.class);
        intent.putExtra(Constants.GAME_KEY, FrontendCache.getGamesForAuthenticatedUser().get(position));
        startActivity(intent);
    }

    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    Handler mHandler = new Handler(Looper.getMainLooper());

    // Create an interface to respond with the result after processing
    public interface OnProcessedListener {
        void onProcessed(boolean success);
    }

    private void loadGamesInBackground(GameCardAdapter.OnGameCardClickListener cardClickListener) {

        final GameLibraryActivity.OnProcessedListener listener = new GameLibraryActivity.OnProcessedListener() {
            @Override
            public void onProcessed(boolean success) {
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressSpinner.setVisibility(View.GONE);
                        GameCardAdapter adapter = new GameCardAdapter(
                                FrontendCache.getGamesForAuthenticatedUser(), cardClickListener);
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