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

import java.util.ArrayList;
import java.util.List;

import Entities.User;

public class AddGameNightInvitesFragment extends Fragment {

    public RecyclerView recyclerView;
    public Button addGameNightFinishButton;
    public FloatingActionButton backButton;
    public Button addInviteeButton;

    private List<User> addedInvitees;

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
        addGameNightFinishButton = rootView.findViewById(R.id.finishButton);
        addGameNightFinishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).onClickFinishButton();
            }
        });

        addInviteeButton = rootView.findViewById(R.id.addInviteeButton);
        addInviteeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddGameNightActivity)getActivity()).showAddInviteeDialog();
            }
        });

        recyclerView = rootView.findViewById(R.id.inviteeRecyclerView);
        // Set up adapter for recycler view
        addedInvitees = new ArrayList<>();
        InviteeAdapter adapter = new InviteeAdapter(addedInvitees, null);
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

    public void addInvitee(User invitee) {
        addedInvitees.add(invitee);
        recyclerView.getAdapter().notifyItemInserted(addedInvitees.size() - 1);
    }
}