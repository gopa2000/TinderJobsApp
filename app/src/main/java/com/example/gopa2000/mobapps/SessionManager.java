package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by gopa2000 on 11/8/16.
 */

public class SessionManager {

    // Session Cache
    private static SessionCache sessionCache = new SessionCache();

    // Shared Preferences
    SharedPreferences pref;

    // Editor object
    SharedPreferences.Editor editor;

    // context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref filename
    private static final String PREF_NAME = "SessionInfo";

    // Shared prefs keys
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    // common
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TYPE = "type";
    public static final String KEY_IMG = "img";
    public static final String KEY_MOBNUM = "mobnum";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_TAGS = "tags";

    // seeker specific
    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_SALUT = "salut";
    public static final String KEY_WORKEXP = "workexp";
    public static final String KEY_EDUCATION = "education";
    public static final String KEY_SKILLS = "skills";

    // employer specific
    public static final String KEY_NAME = "name";
    public static final String KEY_INFO = "info";

    // listing specific
    public static final String KEY_DESC = "jobdesc";
    public static final String KEY_SKILLSREQ = "skillsreq";
    public static final String KEY_OWNER = "owner";

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

            editor.putString(KEY_TYPE, userType);
            editor.putString(KEY_EMAIL, userDetails.getString("email"));
            editor.putString(KEY_MOBNUM, userDetails.getString("mobnum"));
            editor.putString(KEY_IMG, userDetails.getString("img"));
            editor.putString(KEY_LIKES, userDetails.getString("likes"));
            editor.putString(KEY_TAGS, userDetails.getString("tags"));

            // seeker specific
            if(userType.equals("seeker")){
                editor.putString(KEY_SKILLS, userDetails.getString("skills"));
                editor.putString(KEY_WORKEXP, userDetails.getString("workexp"));
                editor.putString(KEY_EDUCATION, userDetails.getString("education"));
                editor.putString(KEY_FNAME, userDetails.getString("fname"));
                editor.putString(KEY_LNAME, userDetails.getString("lnmae"));
                editor.putString(KEY_SALUT, userDetails.getString("salut"));
            }

            // employer specific
            else if(userType.equals("employer")){
                editor.putString(KEY_INFO, userDetails.getString("info"));
                editor.putString(KEY_NAME, userDetails.getString("name"));
            }


            // commit changes
            editor.commit();
        } catch (JSONException e){
            Log.e(TAG, "createLoginSession: ", e);
        }
    }

    public void logoutUser(){
        // clear shared prefs
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public HashMap<String,String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String,String>();

        //email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    // get login state
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public SessionCache getSessionCache(){
        return SessionManager.sessionCache;
    }
}
