package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs_356.app.R;

/**
 * This allows a user to add a game. This activity should be tabbed
 * or fragmented to allow one fragment for scanning the barcode and
 * one fragment for confirming the addition of the game scanned.
 * TODO: Add both fragments.
 */
public class AddGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
    }
}