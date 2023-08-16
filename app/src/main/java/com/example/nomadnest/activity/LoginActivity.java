package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nomadnest.R;
import com.example.nomadnest.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btnSignup.getId()){
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }

    }
}