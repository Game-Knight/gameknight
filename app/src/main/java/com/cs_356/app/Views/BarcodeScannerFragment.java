package com.cs_356.app.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.cs_356.app.R;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.databinding.FragmentBarcodeScannerBinding;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

public class BarcodeScannerFragment extends Fragment {

    private FragmentBarcodeScannerBinding binding;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private String barcodeData;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false);


        initialiseDetectorsAndSources();


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BARCODE_KEY, barcodeData);

                NavHostFragment.findNavController(BarcodeScannerFragment.this)
                        .navigate(
                                R.id.action_BarcodeScannerFragment_to_GameScannedFragment,
                                bundle
                        );
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

    private void initialiseDetectorsAndSources() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this.requireContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this.requireContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        binding.barcodeSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (!checkPermissionsAndStartCamera()) {
                        ActivityCompat.requestPermissions(
                                requireActivity(),
                                new String[] { Manifest.permission.CAMERA },
                                REQUEST_CAMERA_PERMISSION
                        );

                        checkPermissionsAndStartCamera();
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            private boolean checkPermissionsAndStartCamera() throws IOException {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                ) {
                    cameraSource.start(binding.barcodeSurfaceView.getHolder());
                    return true;
                }

                return false;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Intentionally empty.
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    if (barcodes.valueAt(0).displayValue != null) {
                        barcodeData = barcodes.valueAt(0).displayValue;
                    }
                }
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
        initialiseDetectorsAndSources();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}