package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nomadnest.databinding.ActivityHomeBinding;
import com.example.nomadnest.databinding.ActivityPlaceDetailsBinding;

public class PlaceDetailsActivity extends AppCompatActivity {
    private ActivityPlaceDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivityPlaceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}