package com.cs_356.app.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.cs_356.app.R;
import com.cs_356.app.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

/**
 * This is the activity that represents the home view.
 * TODO: Add card components (maybe as fragments?) to display the contents of the home view
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private AppBarConfiguration appBarConfiguration;
	private ActivityHomeBinding binding;
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityHomeBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setSupportActionBar(binding.toolbarHome);

		drawerLayout = findViewById(R.id.drawer_layout_home);
		navigationView = findViewById(R.id.nav_view_home);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawerLayout,
				binding.toolbarHome,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		);

		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		navigationView.setCheckedItem(R.id.nav_home);
		navigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.nav_game_library) {
			startActivity(new Intent(this, GameLibraryActivity.class));
			System.out.println("Clicked on game library!");
		}
		else if (item.getItemId() == R.id.nav_game_nights) {
			startActivity(new Intent(this, GameNightActivity.class));
		}
		else if (item.getItemId() == R.id.nav_settings) {
			// TODO: Add settings navigation if we want!
		}
		return true;
	}
}