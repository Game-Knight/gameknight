package com.game_knight.app.Views.AddGameNight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.game_knight.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddGameNightInfoFragment extends Fragment {

    public TextView selectDateTextView;
    public TextView selectTimeTextView;
    public FloatingActionButton backButton;
    public Button nextButton;
    public EditText nameEditText;
    public EditText descriptionEditText;
    public EditText locationEditText;
    public EditText durationEditText;

    public AddGameNightInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_game_night_info, container, false);

        nameEditText = rootView.findViewById(R.id.gameNightNameEditText);
        descriptionEditText = rootView.findViewById(R.id.gameNightDescriptionEditText);
        locationEditText = rootView.findViewById(R.id.locationEditText);
        durationEditText = rootView.findViewById(R.id.durationEditText);

        backButton = rootView.findViewById(R.id.addGameNightBackFAB);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).onClickBackButton();
            }
        });

        nextButton = rootView.findViewById(R.id.add_game_night_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).saveInfoData(
                    nameEditText.getText().toString(), selectDateTextView.getText().toString(),
                        selectTimeTextView.getText().toString(), locationEditText.getText().toString()
                );
                ((AddGameNightActivity)getActivity()).onClickNextButton();
            }
        });

        selectDateTextView = rootView.findViewById(R.id.selectDateTextView);
        selectDateTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).showDatePickerDialog();
            }
        });

        selectTimeTextView = rootView.findViewById(R.id.selectTimeTextView);
        selectTimeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).showTimePickerDialog();
            }
        });

        return rootView;
    }

    public void setSelectDateTextView(String date) {
        selectDateTextView.setText(date);
    }

    public void setSelectTimeTextView(String time) {
        selectTimeTextView.setText(time);
    }
}