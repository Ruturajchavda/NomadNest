package com.example.nomadnest.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomadnest.activity.EditProfileActivity;
import com.example.nomadnest.activity.PrivacyTOCActivity;
import com.example.nomadnest.databinding.FragmentProfileBinding;
import com.example.nomadnest.session.SessionManager;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private FragmentProfileBinding binding;
    private final String privacyUrl = "file:///android_asset/privacy.html";
    private final String TOSUrl = "file:///android_asset/tos.html";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.tvEditProfile.setOnClickListener(this);
        binding.tvPrivacy.setOnClickListener(this);
        binding.tvTOS.setOnClickListener(this);
        binding.tvSupport.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
        return binding.getRoot();
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
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SessionManager.getUserDetail(getActivity()) != null)
            if (SessionManager.getUserDetail(getActivity()).getProfilePicture() != null)
                binding.ivProfilePicture.setImageBitmap(SessionManager.getUserDetail(getActivity()).getProfilePicture());
    }
}