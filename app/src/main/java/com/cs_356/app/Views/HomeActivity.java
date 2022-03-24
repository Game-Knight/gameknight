package com.cs_356.app.Views;

import android.os.Bundle;

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
public class HomeActivity extends AppCompatActivity {

	private AppBarConfiguration appBarConfiguration;
	private ActivityHomeBinding binding;
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityHomeBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setSupportActionBar(binding.toolbar);

		drawerLayout = findViewById(R.id.drawer_layout);
		navigationView = findViewById(R.id.nav_view);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawerLayout,
				binding.toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		);

		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
	}
}