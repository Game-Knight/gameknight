package com.cs_356.app.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
                    if (upvoteSelected) {
                        upvote.setBackground(getRoundedBackground(v, R.color.shade));
                        downvote.setBackground(getRoundedBackground(v, R.color.shade));
                    } else {
                        upvote.setBackground(getRoundedBackground(v, R.color.green));
                        downvote.setBackground(getRoundedBackground(v, R.color.gray));
                    }
                    upvoteSelected = !upvoteSelected;
                }
            });
            downvote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (downvoteSelected) {
                        upvote.setBackground(getRoundedBackground(v, R.color.shade));
                        downvote.setBackground(getRoundedBackground(v, R.color.shade));
                    } else {
                        upvote.setBackground(getRoundedBackground(v, R.color.gray));
                        downvote.setBackground(getRoundedBackground(v, R.color.red));
                    }
                    downvoteSelected = !downvoteSelected;
                }
            });
        }

        private Drawable getRoundedBackground(View v, int colorId) {
            Drawable background = ResourcesCompat.getDrawable(
                    v.getContext().getResources(),
                    R.drawable.roundcorner,
                    null);
            assert background != null;
            int green = ResourcesCompat.getColor(
                    itemView.getContext().getResources(),
                    colorId,
                    null);
            background.setTint(green);
            return background;
        }
    }
}
