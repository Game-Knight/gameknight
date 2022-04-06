package com.cs_356.app.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.R;
import com.cs_356.app.Views.AddGameNight.AddGameNightActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Entities.User;

public class InviteeAdapter extends RecyclerView.Adapter<InviteeAdapter.ViewHolder> {

    private final List<User> inviteeList;
    private final View.OnClickListener onClickListener;

    public InviteeAdapter(List<User> inviteeList, View.OnClickListener onClickListener) {
        this.inviteeList = inviteeList;
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final ImageView icon;

        private TextView inviteePosition;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.inviteeName);
            icon = (ImageView) itemView.findViewById(R.id.inviteIcon);
            inviteePosition = (TextView) itemView.findViewById(R.id.inviteePosition);
            if (onClickListener != null) {
                itemView.setOnClickListener(onClickListener);
            }
        }

        public TextView getName() {
            return name;
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getInviteePosition() {
            return inviteePosition;
        }
    }

    @Override
    public InviteeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.invitee_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InviteeAdapter.ViewHolder holder, int position) {
        User invitee = inviteeList.get(position);
        holder.getName().setText(invitee.getFullName());
        holder.getIcon().setImageResource(getIconId(invitee.getFirstName()));
        holder.getInviteePosition().setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return inviteeList.size();
    }

    private int getIconId(String name) {
        String lowerName = name.toLowerCase();
        char firstLetter = lowerName.charAt(0);
        switch ((firstLetter - 96) % 6) {
            case 0:
                return R.drawable.ic_bishop;
            case 1:
                return R.drawable.ic_king;
            case 2:
                return R.drawable.ic_rook;
            case 3:
                return R.drawable.ic_knight;
            case 4:
                return R.drawable.ic_pawn;
            case 5:
                return R.drawable.ic_queen;
        }
        return 0;
    }
}
