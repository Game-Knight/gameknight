package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import com.cs_356.app.R;

/**
 * This allows a user to add a game. This activity should be tabbed
 * or fragmented to allow one fragment for scanning the barcode and
 * one fragment for confirming the addition of the game scanned.
 * TODO: Add both fragments.
 */
public class AddGameActivity extends AppCompatActivity {
    public static String EXTRA_MODE = "MODE";
    public static final int MODE_SCANNER = 0;
    public static final int MODE_MANUAL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        int mode = (int) getIntent().getSerializableExtra(EXTRA_MODE);

        if (mode == MODE_SCANNER) {
            View scanFragment = findViewById(R.id.scanFragment);
            scanFragment.setVisibility(View.VISIBLE);
        }
    }
}