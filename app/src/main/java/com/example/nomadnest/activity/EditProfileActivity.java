package com.example.nomadnest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.nomadnest.R;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.ActivityEditProfileBinding;

import com.example.nomadnest.model.User;
import com.example.nomadnest.session.SessionManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEditProfileBinding binding;
    private static final int CAMERA_REQUEST = 1;
    private User currentUser;
    private NomadNestDatabaseHelper nomadNestDatabaseHelper;
    private String userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(this);


        if (nomadNestDatabaseHelper.getUserByEmailId(SessionManager.getInstance().getEmail()) != null) {
            currentUser = nomadNestDatabaseHelper.getUserByEmailId(SessionManager.getInstance().getEmail());
        } else {
            currentUser = new User();
        }

        setUserDetail();

        binding.btnEditProfile.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
    }

    private void setUserDetail() {

        if (currentUser.getEmail() != null && !currentUser.getEmail().isEmpty())
            binding.etEmail.setText(currentUser.getEmail());
        if (currentUser.getName() != null && !currentUser.getName().isEmpty())
            binding.etName.setText(currentUser.getName());
        if (currentUser.getPhone() != null && !currentUser.getPhone().isEmpty())
            binding.etPhone.setText(currentUser.getPhone());
        if (SessionManager.getInstance().getUserImage() != null)
            binding.ivProfilePicture.setImageBitmap(convertBase64ToBitmap(SessionManager.getInstance().getUserImage()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnEditProfile.getId()) {
            showCameraDialog();
        } else if (v.getId() == binding.btnUpdate.getId()) {
            if (isValidate()) {
                currentUser.setPhone(binding.etPhone.getText().toString());
                currentUser.setName(binding.etName.getText().toString());
                nomadNestDatabaseHelper.updateUser(currentUser);
                if (userImage != null) {
                    SessionManager.getInstance().setUserImage(userImage);
                }
                onBackPressed();
            }
        }
    }

    private boolean isValidate() {
        if (binding.etName.getText().toString().trim().equals("")) {
            binding.etName.setError(EditProfileActivity.this.getResources().getString(R.string.err_field_required));
            binding.etName.requestFocus();
            return false;
        }


        if (binding.etPhone.getText().toString().trim().equals("") || binding.etPhone.getText().toString().trim().length() < 10) {
            binding.etPhone.setError(EditProfileActivity.this.getResources().getString(R.string.err_valid_phone));
            binding.etPhone.requestFocus();
            return false;
        }


        removeErrors();
        return true;
    }

    //Clearing all errors on successful validation.
    private void removeErrors() {
        binding.etName.setError(null);
        binding.etPhone.setError(null);
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
                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }
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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            userImage = convertBitmapToBase64(imageBitmap);
            binding.ivProfilePicture.setImageBitmap(imageBitmap);
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedByteArray = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}