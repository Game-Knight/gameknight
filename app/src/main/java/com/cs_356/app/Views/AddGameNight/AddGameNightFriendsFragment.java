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
        User user1 = new User("555", "Brayden", "Wood", "pass");
        User user2 = new User("555", "Chayston", "Wood", "pass");
        User user3 = new User("555", "Jake", "Wood", "pass");
        User user4 = new User("555", "Daniel", "Wood", "pass");
        User user5 = new User("555", "Brayden", "Wood", "pass");
        User user6 = new User("555", "Chayston", "Wood", "pass");
        User user7 = new User("555", "Jake", "Wood", "pass");
        User user8 = new User("555", "Daniel", "Wood", "pass");
        User user9 = new User("555", "Brayden", "Wood", "pass");
        User user10 = new User("555", "Chayston", "Wood", "pass");
        User user11 = new User("555", "Jake", "Wood", "pass");
        User user12 = new User("555", "Daniel", "Wood", "pass");
        User user13 = new User("555", "Brayden", "Wood", "pass");
        User user14 = new User("555", "Chayston", "Wood", "pass");
        User user15 = new User("555", "Jake", "Wood", "pass");
        User user16 = new User("555", "Daniel", "Wood", "pass");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
        users.add(user11);
        users.add(user12);
        users.add(user13);
        users.add(user14);
        users.add(user15);
        users.add(user16);
        InviteeAdapter adapter = new InviteeAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        return rootView;
    }
}