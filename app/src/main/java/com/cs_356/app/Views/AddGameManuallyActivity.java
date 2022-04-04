package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.Utils.Image.PicassoTransformations;
import com.cs_356.app.databinding.ActivityAddGameManuallyBinding;
import com.cs_356.app.databinding.ActivityGameLibraryBinding;
import com.cs_356.app.databinding.GameCardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DataAccess.DAO.APIs.APIBoardGameDAO;
import DataAccess.DataGeneration.APIDB;
import Entities.BoardGame;
import Exceptions.DataAccessException;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class AddGameManuallyActivity extends AppCompatActivity implements GameCardAdapter.OnGameCardClickListener {

    private static final int RESULTS_COUNT = 10;
    private static List<BoardGame> results = new ArrayList<>();

    private ActivityAddGameManuallyBinding binding;
    private GameCardBinding gameCardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddGameManuallyBinding.inflate(getLayoutInflater());
        gameCardBinding = GameCardBinding.inflate(getLayoutInflater(), binding.noResultsImgLinearLayout, true);
        setContentView(binding.getRoot());

        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) binding.progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        binding.searchView.requestFocus();
        GameCardAdapter.OnGameCardClickListener cardClickListener = this;

        binding.searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    binding.addGameManuallyRecyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.noResultsLinearLayout.setVisibility(View.GONE);
                binding.searchView.getQuery();
                loadGamesInBackground(cardClickListener, query);
                binding.searchView.clearFocus();
                binding.addGameManuallyRecyclerView.setVisibility(View.INVISIBLE);
                binding.progressSpinner.setVisibility(View.VISIBLE);

                return true;
            }
        });

        FloatingActionButton backFAB = findViewById(R.id.addManuallyBackFAB);
        backFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GameLibraryActivity.class));
                finish();
            }
        });
    }

    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onGameCardClick(int position) {
        FrontendCache.addGameOwnershipForAuthUser(results.get(position));
        Toast.makeText(this, "Game added!", Toast.LENGTH_SHORT).show();
    }

    public interface OnProcessedListener {
        public void onProcessed(boolean success);
    }

    private void loadGamesInBackground(GameCardAdapter.OnGameCardClickListener cardClickListener, String query) {

        final GameLibraryActivity.OnProcessedListener listener = new GameLibraryActivity.OnProcessedListener() {
            @Override
            public void onProcessed(boolean success) {
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressSpinner.setVisibility(View.GONE);
                        if (results.size() > 0) {
                            GameCardAdapter adapter = new GameCardAdapter(results, cardClickListener);
                            binding.addGameManuallyRecyclerView.setAdapter(adapter);
                            binding.addGameManuallyRecyclerView.setVisibility(View.VISIBLE);
                        }
                        else {
                            Picasso.get().load(Constants.NO_RESULTS_IMG_URL)
                                    .transform(new PicassoTransformations.SCALE_300_MAX())
                                    .into(gameCardBinding.cardImg);
                            Picasso.get().load(Constants.NO_RESULTS_IMG_URL)
                                    .transform(new PicassoTransformations.CROP_SQUARE())
                                    .transform(new PicassoTransformations.SCALE_300_MAX())
                                    .transform(new BlurTransformation(gameCardBinding.cardBg.getContext(),30))
                                    .into(gameCardBinding.cardBg);

                            binding.noResultsLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable() {
            @Override
            public void run() {
                APIBoardGameDAO dao = new APIBoardGameDAO();
                try {
                    results = dao.getBoardGamesByName(query, RESULTS_COUNT);
                }
                catch (DataAccessException e) {
                    e.printStackTrace();
                }

                listener.onProcessed(true);
            }
        };

        mExecutor.execute(backgroundRunnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mExecutor.shutdown();
    }
}