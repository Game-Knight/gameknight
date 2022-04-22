package com.game_knight.app.Views.GameNightView;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game_knight.app.Adapters.InviteeAdapter;
import com.game_knight.app.Cache.FrontendCache;
import com.game_knight.app.R;
import com.game_knight.app.Utils.Constants;
import com.game_knight.app.databinding.FragmentInviteListBinding;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Entities.GameNight;
import Entities.User;

public class InviteListFragment extends Fragment {

    private FragmentInviteListBinding binding;
    private GameNight gameNight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInviteListBinding.inflate(inflater, container, false);

        gameNight = (GameNight) requireActivity().getIntent().getSerializableExtra(Constants.GAME_NIGHT_KEY);

        AnimatedVectorDrawable diceAnimation = (AnimatedVectorDrawable) binding.progressSpinner.getIndeterminateDrawable();

        diceAnimation.registerAnimationCallback(new Animatable2.AnimationCallback(){
            public void onAnimationEnd(Drawable drawable){
                diceAnimation.start();
            }
        });

        loadInvitesInBackground();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    Handler mHandler = new Handler(Looper.getMainLooper());

    // Create an interface to respond with the result after processing
    public interface OnProcessedListener {
        void onProcessed(boolean success);
    }

    private void loadInvitesInBackground() {

        final OnProcessedListener listener = new OnProcessedListener() {
            @Override
            public void onProcessed(boolean success) {
                // Use the handler so we're not trying to update the UI from the bg thread
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressSpinner.setVisibility(View.GONE);
                        List<User> invitees = FrontendCache.getGameNightGuestList(gameNight.getId());
                        InviteeAdapter adapter = new InviteeAdapter(invitees, null);
                        binding.inviteeTabRecyclerView.setAdapter(adapter);
                        mExecutor.shutdown();
                    }
                });
            }
        };

        Runnable backgroundRunnable = new Runnable() {
            @Override
            public void run() {
                binding.progressSpinner.setVisibility(View.VISIBLE);
                FrontendCache.getGameNightGuestList(gameNight.getId())
                        .sort(Comparator.comparing(User::getFullName));

                listener.onProcessed(true);
            }
        };

        mExecutor.execute(backgroundRunnable);
    }
}