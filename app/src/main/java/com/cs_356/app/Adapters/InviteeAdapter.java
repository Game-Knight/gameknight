package com.cs_356.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.R;
import com.cs_356.app.Utils.Image.PicassoTransformations;
import com.cs_356.app.Views.AddGameActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.List;

import Entities.BoardGame;
import Entities.User;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class InviteeAdapter extends RecyclerView.Adapter<InviteeAdapter.ViewHolder> {

    private final List<User> inviteeList;

    public InviteeAdapter(List<User> inviteeList) {
        this.inviteeList = inviteeList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.inviteeName);
        }

        public TextView getName() {
            return name;
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
    }

    @Override
    public int getItemCount() {
        return inviteeList.size();
    }
}
