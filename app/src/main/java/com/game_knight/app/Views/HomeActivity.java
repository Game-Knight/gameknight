package com.game_knight.app.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.game_knight.app.Adapters.GameNightCardAdapter;
import com.game_knight.app.Cache.FrontendCache;
import com.game_knight.app.R;
import com.game_knight.app.Utils.ActivityUtils;
import com.game_knight.app.Utils.Constants;
import com.game_knight.app.Views.GameNightView.GameNightActivity;
import com.game_knight.app.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.GameNight;

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

    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarHome.setTitle("");
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

        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) binding.progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        loadGameNightsInBackground(this, this);
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

    private void loadGameNightsInBackground(GameNightCardAdapter.OnGameNightCardClickListener cardClickListener, Context context){

        final OnProcessedListener listener = new OnProcessedListener() {
            @Override
            public void onProcessed(boolean success){
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        binding.progressSpinner.setVisibility(View.GONE);
                        GameNightCardAdapter adapter = new GameNightCardAdapter(
                                FrontendCache.getGameNightsForAuthenticatedUser(),
                                cardClickListener,
                                context
                        );
                        binding.homeRecyclerView.setAdapter(adapter);
                        mExecutor.shutdown();
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable() {
            @Override
            public void run(){
                binding.progressSpinner.setVisibility(View.VISIBLE);
                FrontendCache.getGameNightsForAuthenticatedUser().sort(Comparator.comparing(GameNight::getName));

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