package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nomadnest.databinding.ActivityBookTripBinding;
import com.example.nomadnest.databinding.ActivityPlaceDetailsBinding;

public class BookTripActivity extends AppCompatActivity {

    private ActivityBookTripBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivityBookTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}