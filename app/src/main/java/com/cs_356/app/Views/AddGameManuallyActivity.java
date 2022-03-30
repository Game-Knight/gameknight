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
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DataAccess.DAO.APIs.APIBoardGameDAO;
import DataAccess.DataGeneration.APIDB;
import Entities.BoardGame;
import Exceptions.DataAccessException;

public class AddGameManuallyActivity extends AppCompatActivity implements GameCardAdapter.OnGameCardClickListener {

    private static final int RESULTS_COUNT = 10;
    private static List<BoardGame> results = new ArrayList<>();
    private static RecyclerView recycler;
    private static ProgressBar progressSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_manually);

        SearchView searchView = findViewById(R.id.searchView);
        recycler = findViewById(R.id.game_library_recycler_view);
        progressSpinner = findViewById(R.id.progressSpinner);
        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        searchView.requestFocus();
        GameCardAdapter.OnGameCardClickListener cardClickListener = this;

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    recycler.setVisibility(View.INVISIBLE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.getQuery();
                loadGamesInBackground(cardClickListener, query);
                searchView.clearFocus();
                recycler.setVisibility(View.INVISIBLE);
                progressSpinner.setVisibility(View.VISIBLE);

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

    }

    public interface OnProcessedListener {
        public void onProcessed(boolean success);
    }

    private void loadGamesInBackground(GameCardAdapter.OnGameCardClickListener cardClickListener, String query){

        final GameLibraryActivity.OnProcessedListener listener = new GameLibraryActivity.OnProcessedListener(){
            @Override
            public void onProcessed(boolean success){
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        progressSpinner.setVisibility(View.GONE);
                        GameCardAdapter adapter = new GameCardAdapter(results, cardClickListener);
                        recycler.setAdapter(adapter);
                        recycler.setVisibility(View.VISIBLE);
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable(){
            @Override
            public void run(){
                APIBoardGameDAO dao = new APIBoardGameDAO();
                try {
                    results = dao.getBoardGamesByName(query, RESULTS_COUNT);
                } catch (DataAccessException e) {
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