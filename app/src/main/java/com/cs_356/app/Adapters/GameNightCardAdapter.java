package com.cs_356.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.cs_356.app.R;
import com.cs_356.app.Utils.DateUtils;

import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import Entities.GameNight;

public class GameNightCardAdapter extends RecyclerView.Adapter<GameNightCardAdapter.ViewHolder> {
    private List<GameNight> gameNights;
    private OnGameNightCardClickListener onGameNightCardClickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTextView;
        private final TextView locationTextView;
        private final TextView dateTimeTextView;
        private final TextView assignmentTextView;
        OnGameNightCardClickListener onGameNightCardClickListener;

        public ViewHolder(View view, OnGameNightCardClickListener onGameNightCardClickListener) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleTextView = (TextView) view.findViewById(R.id.game_night_card_title);
            locationTextView = (TextView) view.findViewById(R.id.game_night_card_location);
            dateTimeTextView = (TextView) view.findViewById(R.id.game_night_card_date_time);
            assignmentTextView = (TextView) view.findViewById(R.id.game_night_card_assignments);
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

        @Override
        public void onClick(View view) {
            onGameNightCardClickListener.onGameNightCardClick(getAdapterPosition());
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet List<GameNight> containing the data to populate views to be used
     * by RecyclerView.
     */
    public GameNightCardAdapter(List<GameNight> dataSet, OnGameNightCardClickListener onGameNightCardClickListener) {
        gameNights = dataSet;
        this.onGameNightCardClickListener = onGameNightCardClickListener;
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
