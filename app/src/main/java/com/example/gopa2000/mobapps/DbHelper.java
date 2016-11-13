package com.example.gopa2000.mobapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gopa2000 on 11/10/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    // db config
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "AppDb";
    private static final String TABLE_LISTINGS = "Listings";
    private static final String TABLE_EMPLOYERS = "Employers";
    private static final String TABLE_SEEKERS = "Seekers";
    private static final String TABLE_MATCHED = "Matched";

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // creating sql tables
    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
