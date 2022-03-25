package com.cs_356.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.databinding.ActivityGameLibraryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Entities.BoardGame;

/**
 * This shows a user's game library. It displays the games on cards, and
 * when you click a game card, it launches the GameViewActivity.
 * TODO: Launch GameViewActivity when a card is clicked.
 */
public class GameLibraryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameLibraryBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FloatingActionButton addFab, scanBarcodeFAB, addManuallyFAB;
    private TextView scanBarcodeText, addManuallyText;
    private CardView expandedFABCard;
    private Button scanBarcodeButton, addManuallyButton;
    private boolean fabExpanded;

    private List<BoardGame> gamesList;


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
        scanBarcodeButton = findViewById(R.id.scanBarcodeButton);
        addManuallyButton = findViewById(R.id.addManuallyButton);


        expandedFABCard.setPivotX(590);
        expandedFABCard.setPivotY(240);
        expandedFABCard.setScaleX(0);
        expandedFABCard.setScaleY(0);

        fabExpanded = false;

        addFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!fabExpanded) {
                            addFab.animate().rotation(45);
                            expandedFABCard.animate().scaleX(1).scaleY(1);
//                            scanBarcodeFAB.show();
//                            addManuallyFAB.show();
//                            addManuallyText.setVisibility(View.VISIBLE);
//                            scanBarcodeText.setVisibility(View.VISIBLE);

                            fabExpanded = true;
                        } else {
                            addFab.animate().rotation(0);
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
                        //TODO launch barcode scanner fragment/activity
                    }
                });
        addManuallyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO (low priority, not needed for demo) launch add manually fragment/activity
                    }
                });

        RecyclerView recycler = findViewById(R.id.game_library_recycler_view);
        gamesList = new ArrayList<>();
        gamesList.add(new BoardGame("311475", "https://cmon-files.s3.amazonaws.com/images/product/avatar/401/avatar.jpg", "https://cmon-files.s3.amazonaws.com/images/product/avatar/401/avatar.jpg", "test",
                "test", 2022, 2, 8,
                10, 10, 15, 12));
        // Create adapter passing in the sample user data
        GameCardAdapter adapter = new GameCardAdapter(gamesList);
        adapter.notifyItemInserted(0);
        // Attach the adapter to the recyclerview to populate items
        recycler.setAdapter(adapter);
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
}