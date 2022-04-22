package com.game_knight.app.Views.AddGameNight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;

import com.game_knight.app.Cache.FrontendCache;
import com.game_knight.app.R;
import com.game_knight.app.Views.GameNightsActivity;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.GameNight;
import Entities.User;
import Enums.RSVP;

/**
 * This activity allows a user to schedule a new game night.
 * This should be a tabbed / fragmented activity, where the date
 * and time scheduling happens on one fragment, and the
 * creation of the invite list happens on another.
 * TODO: Add two fragments, one for scheduling the time and one for adding invites.
 */
public class AddGameNightActivity extends AppCompatActivity {

    private GameNight gameNight;

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

        gameNight = new GameNight(null, FrontendCache.getAuthenticatedUserId(), null, null, null, null);
    }

    public void saveInfoData(String name, String date, String time, String location) {
        gameNight.setName(name);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                cal.toInstant(),
                cal.getTimeZone().toZoneId());
        gameNight.setDate(dateTime);

        gameNight.setLocation(location);
    }

    public void saveInviteData(List<User> invitees) {
        Map<String, RSVP> guestList = new HashMap<>();
        for (User invitee : invitees) {
            guestList.put(invitee.getPhoneNumber(), RSVP.NOT_YET_RESPONDED);
        }
        gameNight.setGuestList(guestList);
    }

    public void saveGameNightData() {
        FrontendCache.addGameNight(gameNight);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showAddInviteeDialog() {
        DialogFragment newFragment = new AddInviteesDialog();
        newFragment.show(getSupportFragmentManager(), "game");
    }

    public void setSelectDateTextView(String date) {
        ((AddGameNightInfoFragment)getSupportFragmentManager().findFragmentById(R.id.addGameNightFragment)).setSelectDateTextView(date);
    }

    public void setSelectTimeTextView(String time) {
        ((AddGameNightInfoFragment)getSupportFragmentManager().findFragmentById(R.id.addGameNightFragment)).setSelectTimeTextView(time);
    }

    public void addInvitee(User invitee) {
        ((AddGameNightInvitesFragment)getSupportFragmentManager().findFragmentById(R.id.addGameNightFragment)).addInvitee(invitee);
    }

    public void removeInvitee(User invitee) {
        ((AddGameNightInvitesFragment)getSupportFragmentManager().findFragmentById(R.id.addGameNightFragment)).removeInvitee(invitee);
    }

    public void onClickBackButton() {
        Intent intent = new Intent(this, GameNightsActivity.class);
        startActivity(intent);
    }

    public void onClickNextButton() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.addGameNightFragment, AddGameNightInvitesFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void onClickFinishButton() {
        Intent intent = new Intent(this, GameNightsActivity.class);
        startActivity(intent);
    }
}