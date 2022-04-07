package com.cs_356.app.Views.GameNightView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.Utils.DateUtils;
import com.cs_356.app.databinding.ActivityGameNightBinding;

import Entities.GameNight;

/**
 * This displays a single game night. If you were the one who created
 * the game night, you'll be able to manage the invite list. This display
 * should show the user which games will be played. It should also allow the
 * user to vote for the games they want to play.
 */
public class GameNightActivity extends AppCompatActivity {

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

        if (FrontendCache.isHostedByAuthenticatedUser(gameNight)) {
            binding.editFab.setVisibility(View.VISIBLE);
            binding.editFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Launch EditGameNightActivity!
                    Toast.makeText(
                            view.getContext(),
                            "Not yet implemented!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }

        binding.gameNightTitle.setText(gameNight.getName());
        binding.gameNightLocation.setText(gameNight.getLocation());
        binding.gameNightDateTime.setText(DateUtils.formatDate(gameNight.getDate()));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(
                this,
                getSupportFragmentManager());
        binding.viewPager.setAdapter(sectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        binding.viewPager.setPadding(
                                0,
                                0,
                                0,
                                binding.gameNightHeader.getHeight());
                    }
                });
    }
}