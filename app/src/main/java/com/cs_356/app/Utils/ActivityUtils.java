package com.cs_356.app.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;

import com.cs_356.app.R;
import com.cs_356.app.Views.GameLibraryActivity;
import com.cs_356.app.Views.GameNightsActivity;
import com.cs_356.app.Views.HomeActivity;
import com.cs_356.app.Views.SettingsActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class ActivityUtils {

    public static DrawerLayout setupDrawerLayout(
            AppCompatActivity activity,
            Toolbar toolbar,
            int drawerId
    ) {
        DrawerLayout drawerLayout = activity.findViewById(drawerId);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        return drawerLayout;
    }

    public static NavigationView setupNavigationView(
            AppCompatActivity activity,
            NavigationView.OnNavigationItemSelectedListener listener,
            int navId,
            int checkedItemId
    ) {
        NavigationView navigationView = activity.findViewById(navId);
        navigationView.bringToFront();

        navigationView.setCheckedItem(checkedItemId);
        navigationView.setNavigationItemSelectedListener(listener);

        return navigationView;
    }

    public static boolean onNavigationItemSelected(
            MenuItem item,
            DrawerLayout drawerLayout,
            NavigationView navigationView,
            AppCompatActivity activity
    ) {
        int curItemId = Objects.requireNonNull(navigationView.getCheckedItem()).getItemId();

        item.setChecked(true);
        drawerLayout.closeDrawers();

        if (item.getItemId() == R.id.nav_item_home
                && curItemId != R.id.nav_item_home) {
            activity.startActivity(new Intent(activity, HomeActivity.class));
            activity.finish();
        }
        else if (item.getItemId() == R.id.nav_item_game_library
                && curItemId != R.id.nav_item_game_library) {
            activity.startActivity(new Intent(activity, GameLibraryActivity.class));
            activity.finish();
        }
        else if (item.getItemId() == R.id.nav_item_game_nights
                && curItemId != R.id.nav_item_game_nights) {
            activity.startActivity(new Intent(activity, GameNightsActivity.class));
            activity.finish();
        }
        else if (item.getItemId() == R.id.nav_item_settings
                && curItemId != R.id.nav_item_settings) {
            activity.startActivity(new Intent(activity, SettingsActivity.class));
            activity.finish();
        }

        return true;
    }

    public static CameraSource initializeCamera(
            Activity activity,
            SurfaceView surfaceView,
            boolean scanBarcodesDetected,
            NavController navController
    ) {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(activity)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        CameraSource cameraSource = new CameraSource.Builder(activity, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission") // The permissions are verified in the activity.
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(surfaceView.getHolder());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
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
                try {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    if (barcodes.size() != 0 && scanBarcodesDetected) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BARCODE_KEY, barcodes.valueAt(0).displayValue);
                        activity.findViewById(R.id.scanOverlay).setForegroundTintList(ColorStateList.valueOf(activity.getColor(R.color.orange)));

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cameraSource.release();
                                navController.navigate(
                                        R.id.action_BarcodeScannerFragment_to_GameScannedFragment,
                                        bundle
                                );
                            }
                        });
                    }
                }
                catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    Toast.makeText(
                            activity,
                            "Oops, there was an error scanning the barcode! Please try again.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        return cameraSource;
    }
}
