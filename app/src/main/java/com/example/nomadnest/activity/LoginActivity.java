package com.example.nomadnest.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nomadnest.R;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.ActivityLoginBinding;
import com.example.nomadnest.model.User;
import com.example.nomadnest.session.SessionManager;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private NomadNestDatabaseHelper nomadNestDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionManager.getInstance().isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
        //Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // assign view to binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

    }

    private void init() {
        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(LoginActivity.this);


        binding.btnSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnLogin.getId()) {
            doLogin();
        } else if (v.getId() == binding.btnSignup.getId()) {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }

    }

    //Login user with correct credentials
    private void doLogin() {
        if (isValidate()) {
            String emailId = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (nomadNestDatabaseHelper.isUser(emailId, password)) {
                SessionManager.getInstance().createLoginSession(emailId);
                startActivity(new HomeActivity());
                finish();
            } else {
                Snackbar.make(binding.layoutMain, LoginActivity.this.getResources().getString(R.string.err_no_user), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    // Validation for form fields
    private boolean isValidate() {
        if (binding.etEmail.getText().toString().trim().equals("")) {
            binding.etEmail.setError(LoginActivity.this.getResources().getString(R.string.err_field_required));
            binding.etEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            binding.etEmail.setError(LoginActivity.this.getResources().getString(R.string.err_valid_mail));
            binding.etEmail.requestFocus();
            return false;
        }

        if (binding.etPassword.getText().toString().trim().equals("")) {
            binding.etPassword.setError(LoginActivity.this.getResources().getString(R.string.err_field_required));
            binding.etPassword.requestFocus();
            return false;
        }

        if (!binding.cbPrivacyPolicy.isChecked()) {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.err_checked_not_privacy), Snackbar.LENGTH_SHORT).show();
            return false;
        }

        removeErrors();
        return true;
    }

    //Clearing all errors on successful validation.
    private void removeErrors() {
        binding.etEmail.setError(null);
        binding.etPassword.setError(null);
    }

    private void startActivity(Activity activity) {
        Intent intent = new Intent(LoginActivity.this, activity.getClass());
        startActivity(intent);
    }
}