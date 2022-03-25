package com.cs_356.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Entities.BoardGame;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class GameCardAdapter extends
        RecyclerView.Adapter<GameCardAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView gameImg;
        public ImageView bgImg;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            gameImg = (ImageView) itemView.findViewById(R.id.card_img);
            bgImg = (ImageView) itemView.findViewById(R.id.card_bg);
        }
    }
    private List<BoardGame> gamesList;

    public GameCardAdapter(List<BoardGame> gamesList) {
        this.gamesList = gamesList;
    }

    @Override
    public GameCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.game_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameCardAdapter.ViewHolder holder, int position) {
        BoardGame game = gamesList.get(position);

        Picasso.get().load(game.getImageUrl()).into(holder.gameImg);
        Picasso.get().load(game.getImageUrl()).transform(new BlurTransformation(holder.bgImg.getContext(),20)).into(holder.bgImg);
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
