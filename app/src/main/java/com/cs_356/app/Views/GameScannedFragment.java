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
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.databinding.FragmentGameScannedBinding;
import com.google.android.gms.vision.CameraSource;

public class GameScannedFragment extends Fragment {

    private FragmentGameScannedBinding binding;
    private CameraSource cameraSource;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGameScannedBinding.inflate(inflater, container, false);
        initializeCamera();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String barcodeRetrieved;
        try {
            assert getArguments() != null;
            barcodeRetrieved = getArguments().getString(Constants.BARCODE_KEY);
        }
        catch (Exception ex) {
            System.out.println("There was no barcode retrieved!");
        }

        // TODO: Use the barcode retrieved to show what game was found!

        binding.gameScannedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameScannedFragment.this)
                        .navigate(R.id.action_GameScannedFragment_to_BarcodeScannerFragment);
            }
        });

        binding.addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Actually add the game!
                requireActivity().startActivity(
                        new Intent(getActivity(), GameLibraryActivity.class)
                );
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cameraSource.release();
        binding = null;
    }

    private void initializeCamera() {
        cameraSource = ActivityUtils.initializeCamera(
                requireActivity(),
                binding.gameScannedSurfaceView,
                false,
                null
        );
    }
}