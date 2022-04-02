package com.cs_356.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.databinding.ActivityGameLibraryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.BoardGame;

/**
 * This shows a user's game library. It displays the games on cards, and
 * when you click a game card, it launches the GameViewActivity.
 * TODO: Launch GameViewActivity when a card is clicked.
 */
public class GameLibraryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GameCardAdapter.OnGameCardClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameLibraryBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton addFab, scanBarcodeFAB, addManuallyFAB;
    private TextView scanBarcodeText, addManuallyText;
    private CardView expandedFABCard;
    private Button scanBarcodeButton, addManuallyButton;
    private ImageButton rotatingAddButton;
    private boolean fabExpanded;

    private RecyclerView recycler;
    private static ProgressBar progressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarGameLibrary);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.game_library);

        drawerLayout = ActivityUtils.setupDrawerLayout(
                this,
                binding.toolbarGameLibrary,
                R.id.drawer_layout_game_library
        );

        navigationView = ActivityUtils.setupNavigationView(
                this,
                this,
                R.id.nav_view_game_library,
                R.id.nav_item_game_library
        );

        addFab = findViewById(R.id.addFAB);

//        scanBarcodeFAB = findViewById(R.id.scanBarcodeFAB);
//        addManuallyFAB = findViewById(R.id.addManuallyFAB);
//        scanBarcodeText = findViewById(R.id.scanBarcodeTextView);
//        addManuallyText = findViewById(R.id.addManuallyTextView);

//        scanBarcodeFAB.hide();
//        addManuallyFAB.hide();
//        scanBarcodeText.setVisibility(View.GONE);
//        addManuallyText.setVisibility(View.GONE);

        expandedFABCard = findViewById(R.id.expandedFABCard);
        rotatingAddButton = findViewById(R.id.addButton);
        scanBarcodeButton = findViewById(R.id.scanBarcodeButton);
        addManuallyButton = findViewById(R.id.addManuallyButton);


        expandedFABCard.setPivotX(590);
        expandedFABCard.setPivotY(240);
        expandedFABCard.setScaleX(0);
        expandedFABCard.setScaleY(0);

        fabExpanded = false;

        rotatingAddButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!fabExpanded) {
                            rotatingAddButton.animate().rotation(45);
                            expandedFABCard.animate().scaleX(1).scaleY(1);
//                            scanBarcodeFAB.show();
//                            addManuallyFAB.show();
//                            addManuallyText.setVisibility(View.VISIBLE);
//                            scanBarcodeText.setVisibility(View.VISIBLE);

                            fabExpanded = true;
                        } else {
                            rotatingAddButton.animate().rotation(0);
                            expandedFABCard.animate().scaleX(0).scaleY(0);
//                            scanBarcodeFAB.hide();
//                            addManuallyFAB.hide();
//                            addManuallyText.setVisibility(View.GONE);
//                            scanBarcodeText.setVisibility(View.GONE);

                            fabExpanded = false;
                        }
                    }
                });

//        scanBarcodeFAB.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {

//                    }
//                });
//        addManuallyFAB.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {

//                    }
//                });

        scanBarcodeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), AddGameActivity.class);
                        intent.putExtra(AddGameActivity.EXTRA_MODE, AddGameActivity.MODE_SCANNER);
                        startActivity(intent);
                        finish();
                    }
                });
        addManuallyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), AddGameManuallyActivity.class);
                        startActivity(intent);
                    }
                });

        recycler = findViewById(R.id.game_library_recycler_view);

        progressSpinner = findViewById(R.id.progressSpinner);
        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        loadGamesInBackground(this);

        // Create adapter passing in the sample user data

//        recycler.setLayoutManager(new LinearLayoutManager(this));
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
    public void onGameCardClick(int position) {
        Intent intent = new Intent(this, GameViewActivity.class);
        intent.putExtra(Constants.GAME_KEY, FrontendCache.getGamesForAuthenticatedUser().get(position));
        startActivity(intent);
    }

    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    Handler mHandler = new Handler(Looper.getMainLooper());

    // Create an interface to respond with the result after processing
    public interface OnProcessedListener {
        public void onProcessed(boolean success);
    }

    private void loadGamesInBackground(GameCardAdapter.OnGameCardClickListener cardClickListener){

        final OnProcessedListener listener = new OnProcessedListener(){
            @Override
            public void onProcessed(boolean success){
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        progressSpinner.setVisibility(View.GONE);
                        GameCardAdapter adapter = new GameCardAdapter(
                                FrontendCache.getGamesForAuthenticatedUser(), cardClickListener);
                        recycler.setAdapter(adapter);
                        mExecutor.shutdown();
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable(){
            @Override
            public void run(){
//                try {
//                    APIDB db = APIDB.getInstance();
//                    gamesList.addAll(db.boardGameTable.values());
//                } catch (DataAccessException e) {
//                    e.printStackTrace();
//                }
                progressSpinner.setVisibility(View.VISIBLE);
                FrontendCache.getGamesForAuthenticatedUser().sort(Comparator.comparing(BoardGame::getName));

                listener.onProcessed(true);
            }
        };

        mExecutor.execute(backgroundRunnable);
    }



}