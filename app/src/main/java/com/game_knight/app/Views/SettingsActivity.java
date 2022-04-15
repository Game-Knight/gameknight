package com.game_knight.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MenuItem;

import com.game_knight.app.R;
import com.game_knight.app.Utils.ActivityUtils;
import com.game_knight.app.databinding.ActivitySettingsBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySettingsBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarSettings.setTitle("");
        setSupportActionBar(binding.toolbarSettings);

        drawerLayout = ActivityUtils.setupDrawerLayout(
                this,
                binding.toolbarSettings,
                R.id.drawer_layout_settings
        );

        navigationView = ActivityUtils.setupNavigationView(
                this,
                this,
                R.id.nav_view_settings,
                R.id.nav_item_settings
        );

        TextInputEditText myPhoneNumber = findViewById(R.id.myPhoneNumber);
        myPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return ActivityUtils.onNavigationItemSelected(
                item,
                drawerLayout,
                navigationView,
                this
        );
    }

}