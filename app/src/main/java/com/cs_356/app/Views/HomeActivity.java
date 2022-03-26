package com.cs_356.app.Views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Utils.DirectDBAccess;
import com.cs_356.app.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

import DataAccess.DataGeneration.InMemoryDB;

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

		drawerLayout = ActivityUtils.setupDrawerLayout(
				this,
				binding.toolbarHome,
				R.id.drawer_layout_home
		);

		navigationView = ActivityUtils.setupNavigationView(
				this,
				this,
				R.id.nav_view_home,
				R.id.nav_item_home
		);

		try {
			DirectDBAccess.getInstance(this);
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
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