package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs_356.app.R;

/**
 * This displays a single game night. If you were the one who created
 * the game night, you'll be able to manage the invite list. This display
 * should show the user which games will be played. It should also allow the
 * user to vote for the games they want to play.
 * TODO: Add a fragment for managing the invite list.
 */
public class GameNightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_night);
    }
}