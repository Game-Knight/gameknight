package com.game_knight.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.game_knight.app.R;

/**
 * This allows a user to add a game. This activity should be tabbed
 * or fragmented to allow one fragment for scanning the barcode and
 * one fragment for confirming the addition of the game scanned.
 * TODO: Add both fragments.
 */
public class AddGameActivity extends AppCompatActivity {
    public static String EXTRA_MODE = "MODE";
    public static final int MODE_SCANNER = 0;
    public static final int MODE_MANUAL = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.CAMERA },
                REQUEST_CAMERA_PERMISSION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        ) {
            int mode = (int) getIntent().getSerializableExtra(EXTRA_MODE);

            if (mode == MODE_SCANNER) {
                View scanFragment = findViewById(R.id.scanFragment);
                scanFragment.setVisibility(View.VISIBLE);
            }
        }
        else {
            Toast.makeText(
                    this,
                    "To use this feature, you must enable camera permissions for this app.",
                    Toast.LENGTH_LONG
            ).show();

            startActivity(new Intent(this, GameLibraryActivity.class));
            finish();
        }
    }
}