package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs_356.app.R;

/**
 * This displays the game nights you have coming up. From this page,
 * you'll be able to add a new game night (which launches the AddGameNightActivity)
 * and you'll be able to click a game night (which launches the GameNightActivity).
 * TODO: Launch AddGameNightActivity when you click the action button.
 * TODO: Launch GameNightActivity when you click a game night.
 */
public class GameNightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_nights);
    }
}