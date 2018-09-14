package com.example.gopa2000.mobapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

import cz.msebera.android.httpclient.Header;

import static com.example.gopa2000.mobapps.DbHelper.TABLE_SEEKERS;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";

    SessionManager sessionManager;
    SessionCache sessionCache;
    DbHelper dbHelper;

    private ArrayList<Boolean> downloadCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager =  new SessionManager(getApplicationContext());
        sessionCache = SessionCache.getInstance();
        dbHelper = new DbHelper(getApplicationContext());
        downloadCompleted = new ArrayList<>();

        //sessionManager.logoutUser();

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

                if(!sessionManager.isLoggedIn()){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
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

        //params.put("timestamp", lastDownloaded);
        RESTClient.get("api/likes", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());

                downloadCompleted.add(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i(TAG, "onSuccess: " + response.toString());
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
                Log.d(TAG, "onSuccess: Listings - " + response.toString());
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

        RESTClient.get("api/matches", params, new JsonHttpResponseHandler(){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: Reached - " + requestCode + " " + resultCode);
        // bit of a hack, since resultCode was always 0 (no clue why).
        // trusting loginActivity to not finish() without legitimate authentication
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
