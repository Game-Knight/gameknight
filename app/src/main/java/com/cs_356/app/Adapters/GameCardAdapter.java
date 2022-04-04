package com.cs_356.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.R;
import com.cs_356.app.Utils.Image.PicassoTransformations;
import com.cs_356.app.Views.AddGameActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import Entities.BoardGame;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.ViewHolder> {

    private List<BoardGame> gamesList;
    private OnGameCardClickListener onGameCardClickListener;

    private int position;
    public static final int CONTEXT_ITEM_DELETE_GAME = 0;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public GameCardAdapter(List<BoardGame> gamesList, OnGameCardClickListener onGameCardClickListener) {
        this.gamesList = gamesList;

        if (onGameCardClickListener != null) {
            this.onGameCardClickListener = onGameCardClickListener;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView gameImg;
        public ImageView bgImg;
        OnGameCardClickListener onGameCardClickListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnGameCardClickListener onGameCardClickListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            itemView.setOnCreateContextMenuListener(this);

            gameImg = (ImageView) itemView.findViewById(R.id.card_img);
            bgImg = (ImageView) itemView.findViewById(R.id.card_bg);

            if (onGameCardClickListener != null) {
                this.onGameCardClickListener = onGameCardClickListener;
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (onGameCardClickListener != null) {
                onGameCardClickListener.onGameCardClick(getAdapterPosition());
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //menuInfo is null
            if (onGameCardClickListener != null) {
                contextMenu.add(Menu.NONE, CONTEXT_ITEM_DELETE_GAME, 0, R.string.delete_game);
            }
        }
    }

    @Override
    public GameCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View gameView = inflater.inflate(R.layout.game_card, parent, false);

        return new ViewHolder(gameView, onGameCardClickListener);
    }

    @Override
    public void onBindViewHolder(GameCardAdapter.ViewHolder holder, int position) {
        BoardGame game = gamesList.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });

        Picasso.get().load(game.getImageUrl()).transform(new PicassoTransformations.SCALE_300_MAX()).into(holder.gameImg);
        Picasso.get().load(game.getImageUrl()).transform(new PicassoTransformations.CROP_SQUARE()).transform(new PicassoTransformations.SCALE_300_MAX()).transform(new BlurTransformation(holder.bgImg.getContext(),30)).into(holder.bgImg);
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public interface OnGameCardClickListener {
        void onGameCardClick(int position);
    }


}
