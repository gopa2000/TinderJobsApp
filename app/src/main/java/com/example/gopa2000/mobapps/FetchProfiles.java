package com.example.gopa2000.mobapps;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by gopa2000 on 11/10/16.
 */

public class FetchProfiles extends AsyncTask<String, Void, ArrayList<CustomCard> > {

    private final String TAG = "Async bg task";
    // this is where API call to retrieve data will be placed

    protected ArrayList<CustomCard> doInBackground(String ...s){
        ArrayList<CustomCard> profiles = new ArrayList<>();

        for(int i=0; i<10; i++){
            //Log.i(TAG, "doInBackground: creating profile object " + i);
            profiles.add(new SeekerClass(i+"fname", i+"lname", null, i+"edu", i+"workExp", i+"Skills", i+"contact", i+"email"));
        }

        return profiles;
    }
}
