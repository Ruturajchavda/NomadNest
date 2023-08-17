package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.nomadnest.R;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.ActivitySplashBinding;
import com.example.nomadnest.model.Places;
import com.example.nomadnest.session.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private NomadNestDatabaseHelper nomadNestDatabaseHelper;
    private int splashTimeOut = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SessionManager.getInstance().isShowIntro()) {
                    startActivity(new IntroScreenActivity());
                } else {
                    startActivity(new LoginActivity());
                }
            }
        }, splashTimeOut);
    }

    private void init() {
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(SplashActivity.this);

        if (SessionManager.getInstance().isFirstTime()) {
            addPlaceData();
            SessionManager.getInstance().createLaunchSession();
            Log.e("TAG", "init: " + "First Time");
        }
    }

    private void addPlaceData() {
        Places places1 = new Places(1, "The Butchart Gardens", getResources().getString(R.string.desc_butchart_gardens),
                "Brentwood Bay, BC", "4.7", R.drawable.travel_2, 650, getResources().getString(R.string.category_nature), true);
        nomadNestDatabaseHelper.addPlaceData(places1);

        Places places2 = new Places(2, "Toronto Islands", getResources().getString(R.string.desc_toronto_ilands),
                "Lake Ontario", "4.7", R.drawable.travel_3, 780, getResources().getString(R.string.category_countryside), true);
        nomadNestDatabaseHelper.addPlaceData(places2);

        Places places3 = new Places(3, "Montmorency Falls", getResources().getString(R.string.desc_montmorency_falls),
                "Quebec, Canada", "4.6", R.drawable.travel_1, 880, getResources().getString(R.string.category_falls), true);
        nomadNestDatabaseHelper.addPlaceData(places3);

        Places places4 = new Places(4, "Algonquin Provincial Park", getResources().getString(R.string.desc_algonquin_provincial_park),
                "Ontario, Canada", "5.0", R.drawable.travel_4, 1020, getResources().getString(R.string.category_nature), false);
        nomadNestDatabaseHelper.addPlaceData(places4);

        Places places5 = new Places(5, "Rideau Canal", getResources().getString(R.string.desc_rideau_canal),
                "Kingston, Ontario", "4.7", R.drawable.travel_5, 560, getResources().getString(R.string.category_countryside), false);
        nomadNestDatabaseHelper.addPlaceData(places5);

        Places places6 = new Places(6, "Niagara Falls", getResources().getString(R.string.desc_niagara_falls),
                "Niagara, Canada", "4.8", R.drawable.travel_6, 840, getResources().getString(R.string.category_falls), false);
        nomadNestDatabaseHelper.addPlaceData(places6);
    }

    private void startActivity(Activity activity) {
        Intent intent = new Intent(SplashActivity.this, activity.getClass());
        startActivity(intent);
        finish();
    }
}