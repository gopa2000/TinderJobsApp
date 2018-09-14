package com.example.gopa2000.mobapps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by gopa2000 on 11/8/16.
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor object
    SharedPreferences.Editor editor;

    // context
    Context context;
    Activity activity;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref filename
    private static final String PREF_NAME = "SessionInfo";

    // Shared prefs keys
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(JSONObject userDetails){
        try {
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGGED_IN, true);

            String userType = userDetails.getString("type");

            editor.putString(DbHelper.KEY_TYPE, userType);

            Log.d(TAG, "createLoginSession: " + userType);
            editor.putString(DbHelper.KEY_EMAIL, userDetails.getJSONObject("info").getString("email"));
            editor.putString(DbHelper.KEY_MOBNUM, userDetails.getJSONObject("info").getString("mobnum"));
            editor.putString(DbHelper.KEY_IMG, userDetails.getJSONObject("info").getString("img"));
            editor.putString(DbHelper.KEY_LIKES, userDetails.getJSONObject("info").getString("likes"));
            editor.putString(DbHelper.KEY_DISLIKES, userDetails.getJSONObject("info").getString("dislikes"));

            // seeker specific
            if(userType.equals("seeker")){
                editor.putString(DbHelper.KEY_SKILLS, userDetails.getJSONObject("info").getString("skills"));
                editor.putString(DbHelper.KEY_WORKEXP, userDetails.getJSONObject("info").getString("workexp"));
                editor.putString(DbHelper.KEY_EDUCATION, userDetails.getJSONObject("info").getString("education"));
                editor.putString(DbHelper.KEY_FNAME, userDetails.getJSONObject("info").getString("fname"));
                editor.putString(DbHelper.KEY_LNAME, userDetails.getJSONObject("info").getString("lname"));
                editor.putString(DbHelper.KEY_SALUT, userDetails.getJSONObject("info").getString("salut"));
                editor.putString(DbHelper.KEY_TAGS, userDetails.getJSONObject("info").getString("tags"));
            }

            // employer specific
            else if(userType.equals("employer")){
                editor.putString(DbHelper.KEY_INFO, userDetails.getJSONObject("info").getString("info"));
                editor.putString(DbHelper.KEY_NAME, userDetails.getJSONObject("info").getString("name"));
            }

            // commit changes
            editor.commit();
            Map<String, ?> prefTest = pref.getAll();

            for(Map.Entry<String, ?> entry : prefTest.entrySet()){
                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            }

        } catch (JSONException e){
            Log.e(TAG, "createLoginSession: ", e);
        }
    }

    public void logoutUser(){
        // clear shared prefs
        editor.clear();
        editor.commit();

        MainActivity.finishAll();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    public Map<String,?> getUserDetails(){
        Map<String,?> user = pref.getAll();
        return user;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(activity, LoginActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.startActivityForResult(intent, 1);
        }
    }

    // get login state
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }
}
