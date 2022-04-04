package com.cs_356.app.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cs_356.app.databinding.FragmentAvailableGamesBinding;

public class AvailableGamesFragment extends Fragment {

    private FragmentAvailableGamesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(Constants.TAB_SECTION_NUMBER);
//        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentAvailableGamesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}