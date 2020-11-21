package com.zzx.mymoviememoir;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.zzx.mymoviememoir.fragments.HomeFragment;
import com.zzx.mymoviememoir.fragments.MapFragment;
import com.zzx.mymoviememoir.fragments.MovieMemoirFragment;
import com.zzx.mymoviememoir.fragments.MovieSearchFragment;
import com.zzx.mymoviememoir.fragments.ReportFragment;
import com.zzx.mymoviememoir.fragments.WatchlistFragment;
import com.zzx.mymoviememoir.user.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private HomeFragment homeFragment;
    private MovieSearchFragment movieSearchFragment;
    private MovieMemoirFragment movieMemoirFragment;
    private WatchlistFragment watchlistFragment;
    private ReportFragment reportFragment;
    private MapFragment mapFragment;
    private TextView tvWelcomeUser, tvCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // adding the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get widget
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // set toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        tvWelcomeUser.setText("Welcome " + UserInfo.getPerFname() + "!");
        SimpleDateFormat s = new SimpleDateFormat("E dd/MM/yyyy");
        tvCurrentDate.setText(s.format(new Date(System.currentTimeMillis())));

        homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        getSupportActionBar().setSubtitle("Homepage");

    }

    // replace fragment by pressing navigation button
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.homepage:
                if (homeFragment == null)
                    homeFragment = new HomeFragment();
                replaceFragment(homeFragment);
                getSupportActionBar().setSubtitle("Homepage");
                break;
            case R.id.movieSearch:
                if (movieSearchFragment == null)
                    movieSearchFragment = new MovieSearchFragment();
                replaceFragment(movieSearchFragment);
                getSupportActionBar().setSubtitle("Movie Search");
                break;
            case R.id.movieMemoir:
                if (movieMemoirFragment == null)
                    movieMemoirFragment = new MovieMemoirFragment();
                replaceFragment(movieMemoirFragment);
                getSupportActionBar().setSubtitle("Movie Memoir");
                break;
            case R.id.watchlist:
                if (watchlistFragment == null)
                    watchlistFragment = new WatchlistFragment();
                replaceFragment(watchlistFragment);
                getSupportActionBar().setSubtitle("Watchlist");
                break;
            case R.id.reports:
                if (reportFragment == null)
                    reportFragment = new ReportFragment();
                replaceFragment(reportFragment);
                getSupportActionBar().setSubtitle("Reports");
                break;
            case R.id.maps:
                if (mapFragment == null)
                    mapFragment = new MapFragment();
                replaceFragment(mapFragment);
                getSupportActionBar().setSubtitle("Maps");
                break;
            case R.id.logout:
                Intent intent = new Intent(MainScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * replace fragment
     * @param nextFragment
     */
    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
