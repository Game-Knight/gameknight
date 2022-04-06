package com.cs_356.app.Views.AddGameNight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs_356.app.Adapters.InviteeAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;

import java.util.ArrayList;
import java.util.List;

import Entities.User;

public class AddInviteesDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<User> searchResults;


    public AddInviteesDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // get root view
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_invitees, null);

        // Set up builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                    }
                })
                .setTitle("Search Users");

        // Set up recycler view
        searchResults = new ArrayList<User>();
        View.OnClickListener itemOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                TextView positionTextView = (TextView) v.findViewById(R.id.inviteePosition);

                int position = Integer.parseInt(String.valueOf(positionTextView.getText()));
                User invitee = searchResults.get(position);

                ImageView checkmark = (ImageView) v.findViewById(R.id.checkmark);
                if (checkmark.getVisibility() == View.INVISIBLE) {
                    checkmark.setVisibility(View.VISIBLE);
                    ((AddGameNightActivity)getActivity()).addInvitee(invitee);
                }
                else {
                    checkmark.setVisibility(View.INVISIBLE);
                    ((AddGameNightActivity)getActivity()).removeInvitee(invitee);
                }
            }
        };
        recyclerView = rootView.findViewById(R.id.inviteeSearchRecyclerView);
        InviteeAdapter adapter = new InviteeAdapter(searchResults, itemOnClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Set up search view
        searchView = rootView.findViewById(R.id.addGameNightSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                updateResultList(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                updateResultList(query);
                return true;
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void updateResultList(String query) {
        List<User> resultList = FrontendCache.getUsersBySearch(query);
        searchResults.clear();
        searchResults.addAll(resultList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}