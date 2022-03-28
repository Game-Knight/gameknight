package com.cs_356.app.Views.AddGameNight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}