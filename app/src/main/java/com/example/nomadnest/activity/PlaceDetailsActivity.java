package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nomadnest.constants.Extras;
import com.example.nomadnest.databinding.ActivityPlaceDetailsBinding;
import com.example.nomadnest.model.Places;

public class PlaceDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPlaceDetailsBinding binding;
    private Places places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign view to binding
        binding = ActivityPlaceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getBundleExtra(Extras.EXTRA_FRAGMENT_BUNDLE);
        if (bundle != null) {
            places = (Places) bundle.getSerializable(Extras.EXTRA_ATTACHMENT);
            //setToolbarTitle(subCategory.getSubCategoryName());
            Log.e("TAG", "onCreate: " + places);
        } else {
            places = new Places();
        }

        init();
    }

    private void init() {

        initializeListener();
        setData();
    }

    private void initializeListener() {
        binding.cardViewBack.setOnClickListener(this);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void setData() {
        binding.textPlaceTitle.setText(places.getPlaceName());
        binding.textPlaceLoction.setText(places.getPlaceLocation());
        binding.textPlaceRating.setText(places.getRatings());
        binding.imagePlace.setImageDrawable(getResources().getDrawable(places.getPlaceImage()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.cardViewBack.getId()) {
            finish();
        }
    }
}