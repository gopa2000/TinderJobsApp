package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "Splash Activity";

    SessionManager sessionManager;
    SessionCache sessionCache;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager =  new SessionManager(getApplicationContext());
        sessionCache = sessionManager.getSessionCache();

        // creates
        dbHelper = new DbHelper(getApplicationContext());

        Hashtable<String, String> last_downloaded = dbHelper.getLastDownloaded();

        downloadEmployers(last_downloaded.get(DbHelper.TABLE_EMPLOYERS));
        downloadSeekers(last_downloaded.get(DbHelper.TABLE_SEEKERS));
        // downloadLikeTable(last_downloaded.get(DbHelper.TABLE_LIKES));
        // downloadListing(last_downloaded.get(DbHelper.TABLE_LISTINGS));


        // get objects passed to sessioncache synchronously for testing purposes
        // syncDebugTest();

        if(DbHelper.doesDatabaseExist(getApplicationContext(), "AppDb")){
            Log.d(TAG, "onCreate: DB exists.");
        } else {
            Log.e(TAG, "onCreate: DB Doesn't exist.");
        }

        Intent intent = new Intent(this, MainActivity.class);
        // startActivity(intent);
    }

    private void syncDebugTest() {
        Log.i(TAG, "onCreate: hello");
        ArrayList<CustomCard> profiles = new ArrayList<>();
        for(int i=0; i<10; i++){
            Log.i(TAG, "doInBackground: creating profile object " + i);
            profiles.add(new SeekerClass(i + "salut", i+"fname", i+"lname", null, i+"edu", i+"workExp", i+"Skills", i+"contact", i+"email", i+"tags"));
        }

        Log.i(TAG, "onCreate: cards arraylist size:" + profiles.size());

        sessionCache.setSessionCards(profiles);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void downloadSeekers(String lastDownloaded){
        RequestParams params = new RequestParams();

        //params.put("last_downloaded", lastDownloaded);
        params.put("timestamp", lastDownloaded);

        RESTClient.get("api/seekers", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(TAG, "onSuccess: " + response.toString());
                dbHelper.storeData(response, DbHelper.TABLE_SEEKERS);
            }
        });
    }

    private void downloadEmployers(String lastDownloaded){
        RequestParams params = new RequestParams();

        params.put("timestamp", lastDownloaded);
        RESTClient.get("api/employers", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(TAG, "onSuccess: " + response.toString());
                dbHelper.storeData(response, DbHelper.TABLE_EMPLOYERS);
            }
        });
    }

    private void downloadLikeTable(String lastDownloaded){
        RequestParams params = new RequestParams();

        params.put("last_downloaded", lastDownloaded);
        RESTClient.get("api/liketable", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                dbHelper.storeData(response, DbHelper.TABLE_LIKES);
            }
        });
    }

    private void downloadListing(String lastDownloaded){
        RequestParams params = new RequestParams();

        params.put("last_downloaded", lastDownloaded);
        RESTClient.get("api/listings", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                dbHelper.storeData(response, DbHelper.TABLE_LISTINGS);
            }
        });
    }

    private void downloadMatched(String lastDownloaded){
        RequestParams params = new RequestParams();

        //params.put("last_downloaded", lastDownloaded);
        params.put("last_downloaded", 0);

        RESTClient.get("api/matched", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                dbHelper.storeData(response, DbHelper.TABLE_MATCHED);
            }
        });
    }

}
