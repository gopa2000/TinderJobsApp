package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "Splash Activity";

    SessionManager sessionManager;
    SessionCache sessionCache;
    SQLiteDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager =  new SessionManager(getApplicationContext());
        sessionCache = sessionManager.getSessionCache();
        dbHelper = new DbHelper(this).getWritableDatabase();

        // get objects passed to sessioncache synchronously for testing purposes
        //syncDebugTest();

        if(doesDatabaseExist(getApplicationContext(), "AppDb")){
            Log.d(TAG, "onCreate: DB exists.");
        } else {
            Log.e(TAG, "onCreate: DB Doesn't exist.");
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void syncDebugTest() {
        Log.i(TAG, "onCreate: hello");
        ArrayList<CustomCard> profiles = new ArrayList<>();
        for(int i=0; i<10; i++){
            Log.i(TAG, "doInBackground: creating profile object " + i);
            profiles.add(new SeekerClass(i + "salut", i+"fname", i+"lname", null, i+"edu", i+"workExp", i+"Skills", i+"contact", i+"email"));
        }

        Log.i(TAG, "onCreate: cards arraylist size:" + profiles.size());

        sessionCache.setSessionCards(profiles);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
