package com.game_knight.app.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game_knight.app.R;
import com.game_knight.app.Utils.ActivityUtils;
import com.game_knight.app.databinding.FragmentBarcodeScannerBinding;
import com.google.android.gms.vision.CameraSource;

public class BarcodeScannerFragment extends Fragment {

    private FragmentBarcodeScannerBinding binding;
    private CameraSource cameraSource;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false);
        initializeCamera();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.scanBarcodeBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().startActivity(
                        new Intent(requireActivity(), GameLibraryActivity.class)
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
                binding.barcodeSurfaceView,
                true,
                NavHostFragment.findNavController(BarcodeScannerFragment.this)
        );
    }
}