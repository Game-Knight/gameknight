package com.cs_356.app.Views.AddGameNight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs_356.app.R;
import com.cs_356.app.Views.GameNightsActivity;

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
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.addGameNightFragment, AddGameNightInfoFragment.class, null)
                    .commit();
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickNextButton() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.addGameNightFragment, AddGameNightFriendsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void onClickFinishButton() {
        Intent intent = new Intent(this, GameNightsActivity.class);
        startActivity(intent);
    }
}