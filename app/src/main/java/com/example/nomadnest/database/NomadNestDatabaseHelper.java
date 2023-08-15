package com.example.nomadnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NomadNestDatabaseHelper extends SQLiteOpenHelper {

    // declare database name and version
    private static final String DATABASE_NAME = "DBGrocery";
    private static final int DATABASE_VERSION = 1;

    public NomadNestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
