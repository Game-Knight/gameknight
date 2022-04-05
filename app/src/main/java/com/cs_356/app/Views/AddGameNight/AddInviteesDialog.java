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

import com.cs_356.app.Adapters.InviteeAdapter;
import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;

import java.util.List;

import Entities.User;

public class AddInviteesDialog extends DialogFragment {

    public RecyclerView recyclerView;

    public AddInviteesDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_invitees, null);
        builder.setView(rootView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                    }
                })
                .setTitle("Search Users");

        recyclerView = rootView.findViewById(R.id.inviteeSearchRecyclerView);
        // Set up adapter for recycler view
        List<User> invitees = FrontendCache.getUserList();
        InviteeAdapter adapter = new InviteeAdapter(invitees);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}