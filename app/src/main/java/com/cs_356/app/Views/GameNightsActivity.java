package com.cs_356.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

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

import com.cs_356.app.Adapters.GameNightCardAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.Views.AddGameNight.AddGameNightActivity;
import com.cs_356.app.Views.GameNightView.GameNightActivity;
import com.cs_356.app.databinding.ActivityGameNightsBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.GameNight;

/**
 * This displays the game nights you have coming up. From this page,
 * you'll be able to add a new game night (which launches the AddGameNightActivity)
 * and you'll be able to click a game night (which launches the GameNightActivity).
 * TODO: Launch AddGameNightActivity when you click the action button.
 * TODO: Launch GameNightActivity when you click a game night.
 */
public class GameNightsActivity
        extends AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            GameNightCardAdapter.OnGameNightCardClickListener
{

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameNightsBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameNightsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addGameNight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickAddNewGameNight(v);
            }
        });

        setSupportActionBar(binding.toolbarGameNights);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.game_nights);

        drawerLayout = ActivityUtils.setupDrawerLayout(
                this,
                binding.toolbarGameNights,
                R.id.drawer_layout_game_nights
        );

        navigationView = ActivityUtils.setupNavigationView(
                this,
                this,
                R.id.nav_view_game_nights,
                R.id.nav_item_game_nights
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

    public void onClickAddNewGameNight(View view) {
        startActivity(new Intent(this, AddGameNightActivity.class));
        finish();
    }

    @Override
    public void onGameNightCardClick(int position) {
        Intent intent = new Intent(this, GameNightActivity.class);
        intent.putExtra(Constants.GAME_NIGHT_KEY, FrontendCache.getGameNightsHostedByAuthenticatedUser().get(position));
        startActivity(intent);
    }

    private void loadGameNightsInBackground(GameNightCardAdapter.OnGameNightCardClickListener cardClickListener, Context context){

        final HomeActivity.OnProcessedListener listener = new HomeActivity.OnProcessedListener() {
            @Override
            public void onProcessed(boolean success){
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        binding.progressSpinner.setVisibility(View.GONE);
                        GameNightCardAdapter adapter = new GameNightCardAdapter(
                                FrontendCache.getGameNightsHostedByAuthenticatedUser(),
                                cardClickListener,
                                context
                        );
                        binding.gameNightsRecyclerView.setAdapter(adapter);
                        mExecutor.shutdown();
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable() {
            @Override
            public void run(){
                binding.progressSpinner.setVisibility(View.VISIBLE);
                FrontendCache.getGameNightsHostedByAuthenticatedUser().sort(Comparator.comparing(GameNight::getName));

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