package com.cs_356.app.Views;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.Adapters.GameNightCardAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.BoardGame;

/**
 * This is the activity that represents the home view.
 * TODO: Add card components (maybe as fragments?) to display the contents of the home view
 */
public class HomeActivity
        extends
            AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            GameNightCardAdapter.OnGameNightCardClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recycler;
    private static ProgressBar progressSpinner;

    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarHome);

        drawerLayout = ActivityUtils.setupDrawerLayout(
                this,
                binding.toolbarHome,
                R.id.drawer_layout_home
        );

        navigationView = ActivityUtils.setupNavigationView(
                this,
                this,
                R.id.nav_view_home,
                R.id.nav_item_home
        );

//        recycler = findViewById(R.id.home_recycler_view);
//        GameNightCardAdapter adapter =
//                new GameNightCardAdapter(FrontendCache.getGameNightsForAuthenticatedUser());
//        recycler.setAdapter(adapter);

        recycler = findViewById(R.id.home_recycler_view);

        progressSpinner = findViewById(R.id.progressSpinner);
        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        loadGameNightsInBackground(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return ActivityUtils.onNavigationItemSelected(
                item,
                drawerLayout,
                navigationView,
                this
        );
    }

    @Override
    public void onGameNightCardClick(int position) {
        Intent intent = new Intent(this, GameNightActivity.class);
        intent.putExtra(Constants.GAME_NIGHT_KEY, FrontendCache.getGameNightsForAuthenticatedUser().get(position));
        startActivity(intent);
    }

    private void loadGameNightsInBackground(GameNightCardAdapter.OnGameNightCardClickListener cardClickListener){

        final OnProcessedListener listener = new OnProcessedListener() {
            @Override
            public void onProcessed(boolean success){
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        progressSpinner.setVisibility(View.GONE);
                        GameNightCardAdapter adapter = new GameNightCardAdapter(
                                FrontendCache.getGameNightList(), cardClickListener);
                        recycler.setAdapter(adapter);
                        mExecutor.shutdown();
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable() {
            @Override
            public void run(){
                progressSpinner.setVisibility(View.VISIBLE);
                FrontendCache.getGamesForAuthenticatedUser().sort(Comparator.comparing(BoardGame::getName));

                listener.onProcessed(true);
            }
        };

        mExecutor.execute(backgroundRunnable);
    }

    // Create an interface to respond with the result after processing
    public interface OnProcessedListener {
        void onProcessed(boolean success);
    }
}