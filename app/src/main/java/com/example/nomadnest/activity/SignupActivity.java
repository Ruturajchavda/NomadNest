package com.example.nomadnest.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nomadnest.R;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.ActivitySignupBinding;
import com.example.nomadnest.model.User;
import com.example.nomadnest.session.SessionManager;
import com.google.android.material.snackbar.Snackbar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding binding;
    private NomadNestDatabaseHelper nomadNestDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // assign view to binding
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    private void init() {
        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(SignupActivity.this);

        binding.btnSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnSignup.getId()) {
            doRegister();
        } else if (v.getId() == binding.btnLogin.getId()) {
            onBackPressed();
        }
    }

    //Register new user with proper validation
    private void doRegister() {
        if (isValidate()) {
            String userName = binding.etName.getText().toString().trim();
            String emailId = binding.etEmail.getText().toString().trim();
            String phone = binding.etPhone.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (!nomadNestDatabaseHelper.isRegistered(emailId)) {
                User user = new User(userName, emailId,phone, password);
                nomadNestDatabaseHelper.addUser(user);
                SessionManager.getInstance().createLoginSession(emailId);

                Snackbar.make(binding.layoutMain, SignupActivity.this.getResources().getString(R.string.registered_successfully), Snackbar.LENGTH_SHORT).show();

                startActivity(new HomeActivity());
                finish();
            } else {
                Snackbar.make(binding.layoutMain, SignupActivity.this.getResources().getString(R.string.err_already_exist), Snackbar.LENGTH_SHORT).show();

            }


        }
    }

    // Validation for form fields
    private boolean isValidate() {
        if (binding.etName.getText().toString().trim().equals("")) {
            binding.etName.setError(SignupActivity.this.getResources().getString(R.string.err_field_required));
            binding.etName.requestFocus();
            return false;
        }

        if (binding.etEmail.getText().toString().trim().equals("")) {
            binding.etEmail.setError(SignupActivity.this.getResources().getString(R.string.err_field_required));
            binding.etEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            binding.etEmail.setError(SignupActivity.this.getResources().getString(R.string.err_valid_mail));
            binding.etEmail.requestFocus();
            return false;
        }

        if (binding.etPhone.getText().toString().trim().equals("")) {
            binding.etPhone.setError(SignupActivity.this.getResources().getString(R.string.err_valid_phone));
            binding.etPhone.requestFocus();
            return false;
        }


        if (binding.etPassword.getText().toString().trim().equals("")) {
            binding.etPassword.setError(SignupActivity.this.getResources().getString(R.string.err_field_required));
            binding.etPassword.requestFocus();
            return false;
        }

        if (binding.etConfirmPassword.getText().toString().trim().equals("")) {
            binding.etConfirmPassword.setError(SignupActivity.this.getResources().getString(R.string.err_field_required));
            binding.etConfirmPassword.requestFocus();
            return false;
        }

        if (!binding.etConfirmPassword.getText().toString().trim().equals(binding.etPassword.getText().toString().trim())) {
            binding.etConfirmPassword.setError(SignupActivity.this.getResources().getString(R.string.err_pass_not_match));
            binding.etConfirmPassword.requestFocus();
            return false;
        }

        if(!binding.cbPrivacyPolicy.isChecked())
        {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.err_checked_not_privacy), Snackbar.LENGTH_SHORT).show();
            return false;
        }

        removeErrors();
        return true;
    }

    //Clearing all errors on successful validation.
    private void removeErrors() {
        binding.etName.setError(null);
        binding.etEmail.setError(null);
        binding.etPhone.setError(null);
        binding.etPassword.setError(null);
        binding.etConfirmPassword.setError(null);
    }

    private void startActivity(Activity activity) {
        Intent intent = new Intent(SignupActivity.this, activity.getClass());
        startActivity(intent);
    }
}