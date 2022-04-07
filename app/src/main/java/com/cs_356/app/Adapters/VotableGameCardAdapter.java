package com.cs_356.app.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.cs_356.app.R;

import java.util.List;

import Entities.BoardGame;

public class VotableGameCardAdapter extends GameCardAdapter {

    public VotableGameCardAdapter(List<BoardGame> gamesList,
                                  OnGameCardClickListener onGameCardClickListener,
                                  Context context) {
        super(gamesList, onGameCardClickListener, context);
    }

    @Override
    public VotableGameCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View gameView = inflater.inflate(R.layout.game_card_voteable, parent, false);

        return new VotableGameCardAdapter.ViewHolder(gameView, onGameCardClickListener);
    }

    public class ViewHolder extends GameCardAdapter.ViewHolder {

        private boolean upvoteSelected = false;
        private boolean downvoteSelected = false;

        public ViewHolder(View itemView, OnGameCardClickListener onGameCardClickListener) {
            super(itemView, onGameCardClickListener);

            final ImageButton upvote = (ImageButton) itemView.findViewById(R.id.upvote);
            final ImageButton downvote = (ImageButton) itemView.findViewById(R.id.downvote);
            // TODO: make these onClick actions actually do something, other than just change the ui
            upvote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Drawable background = ResourcesCompat.getDrawable(
                            itemView.getContext().getResources(),
                            R.drawable.roundcorner,
                            null);
                    assert background != null;
                    int green = ResourcesCompat.getColor(
                            itemView.getContext().getResources(),
                            R.color.green,
                            null);
                    int shade = ResourcesCompat.getColor(
                            itemView.getContext().getResources(),
                            R.color.shade,
                            null);
                    if (upvoteSelected) {
                        background.setTint(shade);
                        upvote.setBackground(background);
                        downvote.setVisibility(View.VISIBLE);
                    } else {
                        background.setTint(green);
                        upvote.setBackground(background);
                        downvote.setVisibility(View.GONE);
                    }
                    upvoteSelected = !upvoteSelected;
                }
            });
            downvote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Drawable background = ResourcesCompat.getDrawable(
                            itemView.getContext().getResources(),
                            R.drawable.roundcorner,
                            null);
                    assert background != null;
                    int red = ResourcesCompat.getColor(
                            itemView.getContext().getResources(),
                            R.color.red,
                            null);
                    int shade = ResourcesCompat.getColor(
                            itemView.getContext().getResources(),
                            R.color.shade,
                            null);
                    if (downvoteSelected) {
                        background.setTint(shade);
                        downvote.setBackground(background);
                        upvote.setVisibility(View.VISIBLE);
                    } else {
                        background.setTint(red);
                        downvote.setBackground(background);
                        upvote.setVisibility(View.GONE);
                    }
                    downvoteSelected = !downvoteSelected;
                }
            });
        }
    }
}
