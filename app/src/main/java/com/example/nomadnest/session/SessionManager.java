package com.example.nomadnest.session;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nomadnest.R;
import com.example.nomadnest.model.User;
import com.google.gson.Gson;

import java.util.HashMap;

// Class for manage sessions(Shared Preference)
public class SessionManager {
    private static final String TAG = "SessionManager";

    // All Shared Preferences Keys
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_EMAIL_ID = "email_id";
    private static final String IS_LOGIN = "is_login";
    public static final String KEY_IS_SHOW_INTRO = "is_show_intro";
    public static final String KEY_IS_FIRST_TIME = "is_first_time";


    private static SessionManager sessionManager;
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;
    // Shared pref mode
    private int PRIVATE_MODE = 0;

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public void initSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(context.getApplicationInfo().packageName, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void createLoginSession(String emailId) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL_ID, emailId);

        // commit changes
        editor.commit();
    }


    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


    public boolean isShowIntro() {
        return pref.getBoolean(KEY_IS_SHOW_INTRO, true);
    }

    public void createIntroSession() {
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_SHOW_INTRO, false);
        // commit changes
        editor.commit();
    }


    public boolean isFirstTime() {
        return pref.getBoolean(KEY_IS_FIRST_TIME, true);
    }

    public void createLaunchSession() {
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_FIRST_TIME, false);
        // commit changes
        editor.commit();
    }

    public final static String userString = "user";

    public static void saveUserDetail(Activity activity, User user) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.user_details), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(userString, json);
        editor.commit();
    }

    public static User getUserDetail(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.user_details), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(userString, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }


}
