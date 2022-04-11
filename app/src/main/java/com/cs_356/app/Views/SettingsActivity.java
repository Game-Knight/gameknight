package com.cs_356.app.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.databinding.ActivitySettingsBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

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