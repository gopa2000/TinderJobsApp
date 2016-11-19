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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import cz.msebera.android.httpclient.Header;

import static com.example.gopa2000.mobapps.DbHelper.TABLE_SEEKERS;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "Splash Activity";

    SessionManager sessionManager;
    SessionCache sessionCache;
    DbHelper dbHelper;

    private ArrayList<Boolean> downloadCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager =  new SessionManager(getApplicationContext());
        sessionCache = sessionManager.getSessionCache();
        dbHelper = new DbHelper(getApplicationContext());
        downloadCompleted = new ArrayList<>();

        Hashtable<String, String> last_downloaded = dbHelper.getLastDownloaded();

        Log.i(TAG, "onCreate: LastModified TABLE_SEEKERS " + last_downloaded.get(TABLE_SEEKERS));
        Log.i(TAG, "onCreate: LastModified TABLE_EMPLOYERS " + last_downloaded.get(DbHelper.TABLE_EMPLOYERS));
        Log.i(TAG, "onCreate: LastModified TABLE_LIKES " + last_downloaded.get(DbHelper.TABLE_LIKES));
        Log.i(TAG, "onCreate: LastModified TABLE_LISTINGS " + last_downloaded.get(DbHelper.TABLE_LISTINGS));
        Log.i(TAG, "onCreate: LastModified TABLE_MATCHED " + last_downloaded.get(DbHelper.TABLE_MATCHED));

        downloadEmployers(last_downloaded.get(DbHelper.TABLE_EMPLOYERS));
        downloadSeekers(last_downloaded.get(TABLE_SEEKERS));
        downloadLikeTable(last_downloaded.get(DbHelper.TABLE_LIKES));
        downloadListing(last_downloaded.get(DbHelper.TABLE_LISTINGS));
        downloadMatched(last_downloaded.get(DbHelper.TABLE_MATCHED));


        // get objects passed to sessioncache synchronously for testing purposes
        // syncDebugTest();

        if(DbHelper.doesDatabaseExist(getApplicationContext(), "AppDb")){
            Log.d(TAG, "onCreate: DB exists.");
        } else {
            Log.e(TAG, "onCreate: DB Doesn't exist.");
        }


        boolean res = true;
        for(boolean bool:downloadCompleted)
            if(bool == false) res = false;


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

        params.put("timestamp", lastDownloaded);

        RESTClient.get("api/seekers", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                downloadCompleted.add(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                if(!response.toString().equals("[]"))
                    dbHelper.storeData(response, TABLE_SEEKERS);

                downloadCompleted.add(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.i(TAG, "onFailure: Cannot reach route.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: Cannot reach route.");
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

                downloadCompleted.add(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                if(!response.toString().equals("[]"))
                    dbHelper.storeData(response, DbHelper.TABLE_EMPLOYERS);

                downloadCompleted.add(true);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }
        });
    }

    private void downloadLikeTable(String lastDownloaded){
        RequestParams params = new RequestParams();

        params.put("timestamp", lastDownloaded);
        RESTClient.get("api/liketable", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                downloadCompleted.add(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                dbHelper.storeData(response, DbHelper.TABLE_LIKES);

                downloadCompleted.add(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }
        });
    }

    private void downloadListing(String lastDownloaded){
        RequestParams params = new RequestParams();

        params.put("timestamp", lastDownloaded);
        RESTClient.get("api/listings", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                downloadCompleted.add(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                dbHelper.storeData(response, DbHelper.TABLE_LISTINGS);

                downloadCompleted.add(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }
        });
    }

    private void downloadMatched(String lastDownloaded){
        RequestParams params = new RequestParams();

        params.put("timestamp", lastDownloaded);

        RESTClient.get("api/matched", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                downloadCompleted.add(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                dbHelper.storeData(response, DbHelper.TABLE_MATCHED);

                downloadCompleted.add(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "onFailure: Cannot reach route.");
            }
        });
    }

}
