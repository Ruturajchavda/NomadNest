package com.example.nomadnest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.MenuItem;

import android.widget.Toast;

import com.example.nomadnest.R;

import com.example.nomadnest.databinding.ActivityHomeBinding;
import com.example.nomadnest.fragment.CalenderFragment;
import com.example.nomadnest.fragment.HomeFragment;
import com.example.nomadnest.fragment.ProfileFragment;
import com.example.nomadnest.interfaces.HostInterface;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, HostInterface {

    private static final String TAG = "HomeActivity";
    private boolean doubleBackToExitPressedOnce = false;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // assign view to binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init()
    {
        changeCurrentFragmentTo(new HomeFragment());

        BottomNavigationView navView = findViewById(R.id.bottomNavView);
        navView.setOnNavigationItemSelectedListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        FragmentManager manager = getSupportFragmentManager();

        if(item.getItemId() == R.id.nav_home){
            HomeFragment homeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
            if (homeFragment == null) {
                fragment = new HomeFragment();
            }
        }else  if(item.getItemId() == R.id.nav_calender){
            CalenderFragment calenderFragment = (CalenderFragment) manager.findFragmentByTag(CalenderFragment.class.getName());
            if (calenderFragment == null) {
                fragment = new CalenderFragment();
            }
        }else  if(item.getItemId() == R.id.nave_profile){
            ProfileFragment profileFragment = (ProfileFragment) manager.findFragmentByTag(ProfileFragment.class.getName());
            if (profileFragment == null) {
                fragment = new ProfileFragment();
            }
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        invalidateOptionsMenu();
        if (fragment != null) {
            FragmentManager fManager = getSupportFragmentManager();
            FragmentTransaction fTransaction = fManager.beginTransaction();
            fTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
            fTransaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void changeCurrentFragmentTo(Fragment fragment) {
        invalidateOptionsMenu();
        Log.e(TAG, "ChangeCurrentFragment");
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        fTransaction.commit();
    }

    ////////////////////Exit From App///////////////////////
    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.press_back_again, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}