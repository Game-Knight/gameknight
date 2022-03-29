package com.cs_356.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Views.AddGameNight.AddGameNightActivity;
import com.cs_356.app.databinding.ActivityGameNightsBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

/**
 * This displays the game nights you have coming up. From this page,
 * you'll be able to add a new game night (which launches the AddGameNightActivity)
 * and you'll be able to click a game night (which launches the GameNightActivity).
 * TODO: Launch AddGameNightActivity when you click the action button.
 * TODO: Launch GameNightActivity when you click a game night.
 */
public class GameNightsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameNightsBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameNightsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button addGameNightButton = findViewById(R.id.add_game_night_button);
        addGameNightButton.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(this, AddGameNightActivity.class);
        startActivity(intent);
    }
}