package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs_356.app.R;

/**
 * This activity allows a user to schedule a new game night.
 * This should be a tabbed / fragmented activity, where the date
 * and time scheduling happens on one fragment, and the
 * creation of the invite list happens on another.
 * TODO: Add two fragments, one for scheduling the time and one for adding invites.
 */
public class AddGameNightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_night);
    }
}