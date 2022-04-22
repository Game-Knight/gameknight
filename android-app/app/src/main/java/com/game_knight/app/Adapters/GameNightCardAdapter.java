package com.game_knight.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.game_knight.app.Cache.FrontendCache;
import com.game_knight.app.R;
import com.game_knight.app.Utils.Constants;
import com.game_knight.app.Utils.DateUtils;
import com.game_knight.app.Views.GameViewActivity;

import java.util.List;

import Entities.BoardGame;
import Entities.GameNight;

public class GameNightCardAdapter extends RecyclerView.Adapter<GameNightCardAdapter.ViewHolder> {
    private List<GameNight> gameNights;
    private OnGameNightCardClickListener onGameNightCardClickListener;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, GameCardAdapter.OnGameCardClickListener
    {
        private final TextView titleTextView;
        private final TextView locationTextView;
        private final TextView dateTimeTextView;
        private final TextView assignmentTextView;
        private final RecyclerView recyclerView;
        OnGameNightCardClickListener onGameNightCardClickListener;

        public ViewHolder(View view, OnGameNightCardClickListener onGameNightCardClickListener) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleTextView = (TextView) view.findViewById(R.id.game_night_card_title);
            locationTextView = (TextView) view.findViewById(R.id.game_night_card_location);
            dateTimeTextView = (TextView) view.findViewById(R.id.game_night_card_date_time);
            assignmentTextView = (TextView) view.findViewById(R.id.game_night_card_assignments);
            recyclerView = (RecyclerView) view.findViewById(R.id.game_night_card_recycler_view);
            this.onGameNightCardClickListener = onGameNightCardClickListener;

            itemView.setOnClickListener(this);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
        public TextView getLocationTextView() {
            return locationTextView;
        }
        public TextView getDateTimeTextView() {
            return dateTimeTextView;
        }
        public TextView getAssignmentTextView() {
            return assignmentTextView;
        }
        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        @Override
        public void onClick(View view) {
            onGameNightCardClickListener.onGameNightCardClick(getAdapterPosition());
        }

        @Override
        public void onGameCardClick(int position) {
            GameCardAdapter adapter = (GameCardAdapter) this.getRecyclerView().getAdapter();

            if (adapter != null
                && adapter.getGamesList() != null
                && adapter.getGamesList().size() > position
                && adapter.getGamesList().get(position) != null) {

                Intent intent = new Intent(context, GameViewActivity.class);
                intent.putExtra(Constants.GAME_KEY, adapter.getGamesList().get(position));
                context.startActivity(intent);
            }
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet List<GameNight> containing the data to populate views to be used
     * by RecyclerView.
     */
    public GameNightCardAdapter(List<GameNight> dataSet, OnGameNightCardClickListener onGameNightCardClickListener, Context context) {
        gameNights = dataSet;
        this.onGameNightCardClickListener = onGameNightCardClickListener;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_night_card, viewGroup, false);
        return new ViewHolder(view, onGameNightCardClickListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        GameNight curr = gameNights.get(position);

        viewHolder.getTitleTextView().setText(curr.getName());
        viewHolder.getLocationTextView().setText(curr.getLocation());
        viewHolder.getDateTimeTextView().setText(DateUtils.formatDate(curr.getDate()));

        List<BoardGame> assignedGames = FrontendCache.getGamesAssignedToAuthenticatedUser(curr);
        if (assignedGames.size() == 0) {
            viewHolder.getAssignmentTextView().setText(R.string.nothing_to_bring);
        }

        GameCardAdapter adapter = new GameCardAdapter(assignedGames, viewHolder, context);
        viewHolder.getRecyclerView().setAdapter(adapter);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return gameNights.size();
    }

    public interface OnGameNightCardClickListener {
        void onGameNightCardClick(int position);
    }
}
