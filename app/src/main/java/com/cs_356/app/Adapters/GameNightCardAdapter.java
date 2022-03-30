package com.cs_356.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cs_356.app.R;

import java.util.List;

import Entities.BoardGame;
import Entities.GameNight;

public class GameNightCardAdapter extends RecyclerView.Adapter<GameNightCardAdapter.ViewHolder> {
    private List<GameNight> gameNights;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView locationTextView;
        private final TextView dateTimeTextView;
        private final TextView assignmentTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleTextView = (TextView) view.findViewById(R.id.game_night_card_title);
            locationTextView = (TextView) view.findViewById(R.id.game_night_card_location);
            dateTimeTextView = (TextView) view.findViewById(R.id.game_night_card_date_time);
            assignmentTextView = (TextView) view.findViewById(R.id.game_night_card_assignments);
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
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet List<GameNight> containing the data to populate views to be used
     * by RecyclerView.
     */
    public GameNightCardAdapter(List<GameNight> dataSet) {
        gameNights = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_night_card, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitleTextView().setText(gameNights.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return gameNights.size();
    }

}
