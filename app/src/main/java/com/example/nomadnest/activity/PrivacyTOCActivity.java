package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.nomadnest.R;
import com.example.nomadnest.databinding.ActivityPrivacyTocactivityBinding;

public class PrivacyTOCActivity extends AppCompatActivity {

    private ActivityPrivacyTocactivityBinding binding;
    private String privacyUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding = ActivityPrivacyTocactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        privacyUrl = getIntent().getStringExtra("url");
        binding.wvPrivacy.loadUrl(privacyUrl);

        if(privacyUrl.contains("privacy")){
            this.setTitle(R.string.privacy_policy_text);
        }else{
            this.setTitle(R.string.terms_of_service);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}