package com.cs_356.app.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs_356.app.R;
import com.cs_356.app.databinding.FragmentBarcodeScannerBinding;

import java.util.Objects;

public class BarcodeScannerFragment extends Fragment {

    private FragmentBarcodeScannerBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BarcodeScannerFragment.this)
                        .navigate(R.id.action_BarcodeScannerFragment_to_GameScannedFragment);
            }
        });

        binding.scanBarcodeBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().startActivity(
                        new Intent(getActivity(), GameLibraryActivity.class)
                );
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}