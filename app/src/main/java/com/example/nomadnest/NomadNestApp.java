package com.example.nomadnest;

import android.app.Application;

import com.example.nomadnest.session.SessionManager;

public class NomadNestApp extends Application {
    private static final String TAG = NomadNestApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //Initiate Session Manger class to access store Shared Preferences
        SessionManager.getInstance().initSessionManager(getApplicationContext());
    }
}

