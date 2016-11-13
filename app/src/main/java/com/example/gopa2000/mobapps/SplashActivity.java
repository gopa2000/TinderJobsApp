package com.example.gopa2000.mobapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "Splash Activity";

    SessionManager sessionManager;
    SessionCache sessionCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionCache = SessionCache.getInstance();

        // get objects passed to sessioncache synchronously for testing purposes
        //syncDebugTest();

        Log.i(TAG, "onCreate: hello");
        ArrayList<CustomCard> profiles = new ArrayList<>();
        for(int i=0; i<10; i++){
            Log.i(TAG, "doInBackground: creating profile object " + i);
            profiles.add(new SeekerClass(i + "salut", i+"fname", i+"lname", null, i+"edu", i+"workExp", i+"Skills", i+"contact", i+"email"));
        }

        Log.i(TAG, "onCreate: cards arraylist size:" + profiles.size());

        sessionCache.setSessionCards(profiles);
       /* sessionManager = new SessionManager(getApplicationContext());

        sessionManager.logoutUser();*/


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // uncomment the following line and the matching closing comment markup
/*
        FetchProfiles taskCacheCards = new FetchProfiles() {
            @Override
            protected void onPostExecute(ArrayList<CustomCard> cards){
                sessionCache.setSessionCards(cards);

                Log.i(TAG, "SplashActivity");
                for(CustomCard card:cards){
                    Log.i(TAG, card.toString());
                }

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
*/
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
}
