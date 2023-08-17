package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.nomadnest.R;
import com.example.nomadnest.databinding.ActivityEditProfileBinding;

import com.example.nomadnest.model.User;
import com.example.nomadnest.session.SessionManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEditProfileBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(SessionManager.getUserDetail(this) != null)
            currentUser = SessionManager.getUserDetail(this);
        else
            currentUser = new User();
        setUserDetail();

        binding.btnEditProfile.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
    }

    private void setUserDetail() {
        if(currentUser.getEmail() != null && !currentUser.getEmail().isEmpty())
            binding.etEmail.setText(currentUser.getEmail());
        if(currentUser.getName() != null && !currentUser.getName().isEmpty())
            binding.etName.setText(currentUser.getName());
        if(currentUser.getPhone() != null && !currentUser.getPhone().isEmpty())
            binding.etPhone.setText(currentUser.getPhone());
        if(currentUser.getProfilePicture() != null)
            binding.ivProfilePicture.setImageBitmap(currentUser.getProfilePicture());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnEditProfile.getId()) {
            showCameraDialog();
        }else if (v.getId() == binding.btnUpdate.getId()) {
            currentUser.setEmail(binding.etEmail.getText().toString());
            currentUser.setPhone(binding.etPhone.getText().toString());
            currentUser.setName(binding.etName.getText().toString());
            SessionManager.saveUserDetail(this,currentUser);
            onBackPressed();
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

    private void showCameraDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.open_camera_tile))
                .setMessage(getString(R.string.open_camera_question))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.ivProfilePicture.setImageBitmap(photo);
            currentUser.setProfilePicture(photo);
        }
    }

}