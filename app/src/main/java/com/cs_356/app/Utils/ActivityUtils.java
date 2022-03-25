package com.cs_356.app.Utils;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cs_356.app.R;
import com.cs_356.app.Views.GameLibraryActivity;
import com.cs_356.app.Views.GameNightsActivity;
import com.cs_356.app.Views.HomeActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
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
            // TODO: Add settings navigation if we want!
        }

        return true;
    }
}
