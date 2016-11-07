package com.example.gopa2000.mobapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

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

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref filename
    private static final String PREF_NAME = "SessionInfo";

    // Shared prefs keys
    private static final String IS_LOGGED_IN = "IsLoggedIn";

    // public
    public static final String KEY_EMAIL = "email";

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGGED_IN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
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
}
