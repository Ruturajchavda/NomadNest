package com.example.nomadnest.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomadnest.R;
import com.example.nomadnest.activity.EditProfileActivity;
import com.example.nomadnest.activity.LoginActivity;
import com.example.nomadnest.activity.PrivacyTOCActivity;
import com.example.nomadnest.database.NomadNestDatabaseHelper;
import com.example.nomadnest.databinding.FragmentProfileBinding;
import com.example.nomadnest.model.User;
import com.example.nomadnest.session.SessionManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private FragmentProfileBinding binding;
    private final String privacyUrl = "file:///android_asset/privacy.html";
    private final String TOSUrl = "file:///android_asset/tos.html";
    private NomadNestDatabaseHelper nomadNestDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        //Initialize Database
        nomadNestDatabaseHelper = new NomadNestDatabaseHelper(getActivity());

        binding.tvEditProfile.setOnClickListener(this);
        binding.tvPrivacy.setOnClickListener(this);
        binding.tvTOS.setOnClickListener(this);
        binding.tvSupport.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
        binding.tvDeleteAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.tvEditProfile.getId()) {
            getActivity().startActivity(new Intent(getActivity(), EditProfileActivity.class));
        } else if (v.getId() == binding.tvPrivacy.getId()) {
            Intent intent = new Intent(getActivity(), PrivacyTOCActivity.class);
            intent.putExtra("url", privacyUrl);
            startActivity(intent);
        } else if (v.getId() == binding.tvTOS.getId()) {
            Intent intent = new Intent(getActivity(), PrivacyTOCActivity.class);
            intent.putExtra("url", TOSUrl);
            startActivity(intent);
        } else if (v.getId() == binding.tvSupport.getId()) {
            Snackbar.make(binding.layoutMain, getResources().getString(R.string.support_text), Snackbar.LENGTH_SHORT).show();

        } else if (v.getId() == binding.tvLogout.getId()) {
            doLogout();
        } else if (v.getId() == binding.tvDeleteAccount.getId()) {
            deleteAccount();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SessionManager.getInstance().getUserImage() != null)
            binding.ivProfilePicture.setImageBitmap(convertBase64ToBitmap(SessionManager.getInstance().getUserImage()));

        showUserData();
    }

    private void showUserData() {
        User user = nomadNestDatabaseHelper.getUserByEmailId(SessionManager.getInstance().getEmail());
        binding.tvUsername.setText(user.getName());
        binding.tvEmail.setText(user.getEmail());
    }

    private Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedByteArray = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    //Delete Account Permanently
    private void deleteAccount() {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getString(R.string.title_delete))
                .setMessage(getString(R.string.delete_question))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = nomadNestDatabaseHelper.getUserByEmailId(SessionManager.getInstance().getEmail());
                        nomadNestDatabaseHelper.deleteUserByEmail(user.getEmail());
                        SessionManager.getInstance().logoutUser();
                        SessionManager.getInstance().setUserImage(null);

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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

    //Logout
    private void doLogout() {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getString(R.string.title_log_out))
                .setMessage(getString(R.string.log_out_question))
                .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SessionManager.getInstance().logoutUser();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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

}