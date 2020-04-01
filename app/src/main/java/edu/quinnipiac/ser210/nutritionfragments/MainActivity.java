package edu.quinnipiac.ser210.nutritionfragments;
//imports

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/*
Christian Mele
April 31, 2020
MainActivity Class
This class makes and populates the navigation drawer, as well as provides buttons for operating it and specifying
    how to open each fragment in the navigation drawer and which one opens on startup
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_data:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NutritionFragment()).commit();
                break;
            //makes a toast of the about text when the about button is clicked
            case R.id.nav_about:
                Toast.makeText(this, "Enter a food name and click Enter to submit the food, then Go to retrieve the food data, the app will return basic nutrition facts about your entered food.", Toast.LENGTH_SHORT).show();
                break;
            //makes a toast of the info text when the info button is clicked
            case R.id.nav_info:
                Toast.makeText(this, "This app uses edamam API with RapidAPI to access a food database and returns the most popular result from each query.", Toast.LENGTH_SHORT).show();
                break;
        }
        //button for closing the navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //button for closing the navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}