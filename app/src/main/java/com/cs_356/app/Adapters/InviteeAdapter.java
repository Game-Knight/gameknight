package com.cs_356.app.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Entities.User;

public class InviteeAdapter extends RecyclerView.Adapter<InviteeAdapter.ViewHolder> {

    private final List<User> inviteeList;

    public InviteeAdapter(List<User> inviteeList) {
        this.inviteeList = inviteeList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.inviteeName);
            icon = (ImageView) itemView.findViewById(R.id.inviteIcon);
        }

        public TextView getName() {
            return name;
        }

        public ImageView getIcon() {
            return icon;
        }
    }

    @Override
    public InviteeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.invitee_item, parent, false);
        return new ViewHolder(gameView);
    }

    @Override
    public void onBindViewHolder(InviteeAdapter.ViewHolder holder, int position) {
        User invitee = inviteeList.get(position);
        holder.getName().setText(invitee.getFullName());
        holder.getIcon().setImageResource(getRandomIconId());
    }

    @Override
    public int getItemCount() {
        return inviteeList.size();
    }

    private int getRandomIconId() {
        List<Integer> icons = Arrays.asList(
                R.drawable.ic_bishop,
                R.drawable.ic_king,
                R.drawable.ic_knight,
                R.drawable.ic_pawn,
                R.drawable.ic_queen,
                R.drawable.ic_rook);
        Random rand = new Random();
        return icons.get(rand.nextInt(icons.size()));
    }
}
