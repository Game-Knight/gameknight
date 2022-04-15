package com.game_knight.app.Views.AddGameNight;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.game_knight.app.R;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current time as the default values for the picker
//        final Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
        // Use 12 pm as the default value for the picker
        int hour = 12;
        int minute = 0;


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String modifier;
        if (hourOfDay >= 12) {
            if (hourOfDay != 12) {
                hourOfDay = hourOfDay % 12;
            }
            modifier = " pm";
        }
        else {
            if (hourOfDay == 0) {
                hourOfDay = 12;
            }
            modifier = " am";
        }
        String minuteString = (minute ==  0 ? '0' + Integer.toString(minute) : Integer.toString(minute));
        String time = Integer.toString(hourOfDay) + ":" + minuteString + modifier;
        ((AddGameNightActivity)getActivity()).setSelectTimeTextView(time);
    }
}