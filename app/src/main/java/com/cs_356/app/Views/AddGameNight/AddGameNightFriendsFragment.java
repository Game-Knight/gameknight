package com.cs_356.app.Views.AddGameNight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs_356.app.Adapters.GameCardAdapter;
import com.cs_356.app.Adapters.InviteeAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;

import java.util.ArrayList;
import java.util.List;

import Entities.User;

public class AddGameNightFriendsFragment extends Fragment {

    public RecyclerView recyclerView;
    public Button addGameNightFinishButton;

    public AddGameNightFriendsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_add_game_night_friends, container, false);

        // Set on click for finish button
        addGameNightFinishButton = rootView.findViewById(R.id.add_game_night_finish_button);
        addGameNightFinishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).onClickFinishButton();
            }
        });

        recyclerView = rootView.findViewById(R.id.inviteeRecyclerView);
        // Set up adapter for recycler view
        List<User> invitees = FrontendCache.getUserList();
        InviteeAdapter adapter = new InviteeAdapter(invitees);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        return rootView;
    }
}