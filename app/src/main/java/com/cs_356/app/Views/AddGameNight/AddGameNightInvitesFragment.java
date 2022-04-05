package com.cs_356.app.Views.AddGameNight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs_356.app.Adapters.InviteeAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import Entities.User;

public class AddGameNightInvitesFragment extends Fragment {

    public RecyclerView recyclerView;
    public Button addGameNightFinishButton;
    public FloatingActionButton backButton;

    public AddGameNightInvitesFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_add_game_night_invites, container, false);

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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        backButton = rootView.findViewById(R.id.addGameNightBackFAB);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        return rootView;
    }
}