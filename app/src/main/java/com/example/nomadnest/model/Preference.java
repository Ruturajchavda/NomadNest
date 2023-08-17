package com.example.nomadnest.model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.nomadnest.R;
import com.google.gson.Gson;

public class Preference {

    public final static String userString = "user";

    public static void saveUserDetail(Activity activity,User user){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.user_details), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(userString, json);
        editor.commit();
    }

    public static User getUserDetail(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.user_details), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(userString, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }
}
