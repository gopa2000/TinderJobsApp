package com.example.gopa2000.mobapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gopa2000 on 11/10/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    private final String TAG = "SQLiteOpenHelper";

    // db config
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "AppDb";
    private static final String TABLE_LISTINGS = "Listings";
    private static final String TABLE_EMPLOYERS = "Employers";
    private static final String TABLE_SEEKERS = "Seekers";
    private static final String TABLE_LIKES = "LikeTable";
    private static final String TABLE_LASTMODIFIED = "LastModified";

    // table columns


    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // creating sql tables
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_SEEKERS + ".");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEEKERS + "(" +
                SessionManager.KEY_SALUT + " TEXT, " + SessionManager.KEY_FNAME + " TEXT, " +
                SessionManager.KEY_LNAME + " TEXT, " + SessionManager.KEY_EMAIL + " TEXT PRIMARY KEY, " +
                SessionManager.KEY_EDUCATION + " TEXT, " + SessionManager.KEY_IMG + " TEXT, " +
                SessionManager.KEY_WORKEXP + " TEXT, " + SessionManager.KEY_MOBNUM + " TEXT, " +
                SessionManager.KEY_TAGS + " TEXT, " + SessionManager.KEY_LIKES + " TEXT " +")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_EMPLOYERS + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYERS + "(" +
                SessionManager.KEY_NAME + " TEXT, " + SessionManager.KEY_EMAIL + " TEXT PRIMARY KEY, " +
                SessionManager.KEY_IMG + " TEXT, " + SessionManager.KEY_MOBNUM + " TEXT, " +
                SessionManager.KEY_INFO + " TEXT, " + SessionManager.KEY_LIKES + " TEXT "+ ")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LISTINGS + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LISTINGS + "(" +
                SessionManager.KEY_DESC + " TEXT PRIMARY KEY, " + SessionManager.KEY_TAGS + " TEXT," +
                SessionManager.KEY_OWNER + " TEXT, " + SessionManager.KEY_SKILLSREQ +" TEXT " + ")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LIKES);
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LIKES + "(" +
                "LIKER TEXT, LIKEE TEXT" + ")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LASTMODIFIED + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LASTMODIFIED + "(" +
                TABLE_EMPLOYERS + " TEXT, " + TABLE_SEEKERS + " TEXT, " +
                TABLE_LISTINGS + " TEXT, " + TABLE_LIKES + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LASTMODIFIED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEEKERS);

        onCreate(db);
    }
}
