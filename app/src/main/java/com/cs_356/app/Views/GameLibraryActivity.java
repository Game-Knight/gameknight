package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs_356.app.R;

/**
 * This shows a user's game library. It displays the games on cards, and
 * when you click a game card, it launches the GameViewActivity.
 * TODO: Launch GameViewActivity when a card is clicked.
 */
public class GameLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Game library created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_library);
    }
}