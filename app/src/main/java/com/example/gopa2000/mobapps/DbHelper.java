package com.example.gopa2000.mobapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by gopa2000 on 11/10/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    private final String TAG = "SQLiteOpenHelper";

    // went through this much trouble cos had to map to json return objects from the backend - not modifying 20 million
    // queries for a typo

    // db config
    private static final int DB_VERSION = 11;
    public static final String DB_NAME              = "AppDb";
    public static final String TABLE_LISTINGS       = "Listings";
    public static final String TABLE_EMPLOYERS      = "Employers";
    public static final String TABLE_SEEKERS        = "Seekers";
    public static final String TABLE_LIKES          = "LikeTable";
    public static final String TABLE_LASTMODIFIED   = "LastModified";
    public static final String TABLE_MATCHED        = "MatchedTable";


    // common
    public static final String KEY_EMAIL        = "email";
    public static final String KEY_TYPE         = "type";
    public static final String KEY_IMG          = "img";
    public static final String KEY_MOBNUM       = "mobnum";
    public static final String KEY_LIKES        = "likes";
    public static final String KEY_TAGS         = "tags";
    public static final String KEY_DISLIKES     = "dislikes";

    // seeker specific
    public static final String KEY_FNAME        = "fname";
    public static final String KEY_LNAME        = "lname";
    public static final String KEY_SALUT        = "salut";
    public static final String KEY_WORKEXP      = "workexp";
    public static final String KEY_EDUCATION    = "education";
    public static final String KEY_SKILLS       = "skills";

    // employer specific
    public static final String KEY_NAME         = "name";
    public static final String KEY_INFO         = "info";

    // listing specific
    public static final String KEY_DESC         = "jobdesc";
    public static final String KEY_SKILLSREQ    = "skillsreq";
    public static final String KEY_OWNER        = "owner";
    public static final String KEY_JOBTITLE     = "jobTitle";
    public static final String KEY_EXPREQ       = "expRequired";

    // like table specific
    public static final String KEY_LIKEE        = "likee";
    public static final String KEY_LIKER        = "liker";

    // matched table specific
    public static final String KEY_SEEKER       = "seeker";
    public static final String KEY_EMPLOYER     = "employer";


    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    // creating sql tables
    @Override
    public void onCreate(SQLiteDatabase db){

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_SEEKERS + ".");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEEKERS + "(" +
                KEY_SALUT + " TEXT, " + KEY_FNAME + " TEXT, " +
                KEY_LNAME + " TEXT, " + KEY_EMAIL + " VARCHAR(320) PRIMARY KEY, " +
                KEY_EDUCATION + " TEXT, " + KEY_IMG + " TEXT, " +
                KEY_WORKEXP + " TEXT, " + KEY_MOBNUM + " TEXT, " +
                KEY_TAGS + " TEXT, " + KEY_LIKES + " TEXT, " +
                KEY_DISLIKES + " TEXT," + KEY_SKILLS + " TEXT)";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_EMPLOYERS + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYERS + "(" +
                KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT PRIMARY KEY, " +
                KEY_IMG + " TEXT, " + KEY_MOBNUM + " TEXT, " +
                KEY_INFO + " TEXT, " + KEY_LIKES + " TEXT, " +
                KEY_DISLIKES + " TEXT" +")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LISTINGS + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LISTINGS + "(" +
                KEY_DESC + " TEXT PRIMARY KEY, " + KEY_TAGS + " TEXT," +
                KEY_OWNER + " TEXT, " + KEY_SKILLSREQ +" TEXT " +
                KEY_JOBTITLE + " TEXT)";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LIKES);
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LIKES + "(" +
                "TYPE TEXT, LIKER TEXT, LIKEE TEXT" + ")";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE" + TABLE_MATCHED);
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MATCHED + "(" +
                "SEEKER TEXT, EMPLOYER TEXT);";

        db.execSQL(CREATE_TABLE);

        Log.d(TAG, "onCreate: " + "Create TABLE " + TABLE_LASTMODIFIED + ".");
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LASTMODIFIED + "(" +
                "TABLE_NAME TEXT, LAST_MODIFIED TEXT)";

        db.execSQL(CREATE_TABLE);

        ArrayList<String> TableNames = new ArrayList<>(Arrays.asList(TABLE_SEEKERS,TABLE_EMPLOYERS,TABLE_LISTINGS,TABLE_LIKES,TABLE_MATCHED));

        for(String name:TableNames) {
            Log.d(TAG, "onCreate: Adding " + name + " init value to TABLE_LASTMODIFIED");
            String ADD_TABLENAMES = "INSERT INTO " + TABLE_LASTMODIFIED + " VALUES (\"" + name + "\", \"1\")";
            db.execSQL(ADD_TABLENAMES);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LASTMODIFIED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEEKERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHED);

        onCreate(db);
    }

    public Hashtable<String, String> getLastDownloaded(){
        Hashtable<String, String> res =  new Hashtable<>();

        String selectQuery  = "SELECT * FROM " + TABLE_LASTMODIFIED + ";";
        SQLiteDatabase db   = this.getReadableDatabase();

        try (Cursor cursor =  db.rawQuery(selectQuery, null)) {
            while (cursor.moveToNext()) {
                res.put(cursor.getString(0), cursor.getString(1));
            }
            cursor.close();
        }
        return res;
    }

    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void storeData(JSONArray response, String TABLE_NAME){

        // define SQL queries here
        String INSERT_QUERY = "";
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            for(int i=0; i<response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);

                ContentValues values = new ContentValues();

                if(TABLE_NAME.equals(TABLE_SEEKERS)) {

                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_SALUT, KEY_FNAME, KEY_LNAME, KEY_EMAIL, KEY_EDUCATION ,KEY_IMG, KEY_WORKEXP,KEY_MOBNUM, KEY_TAGS, KEY_LIKES, KEY_DISLIKES, KEY_SKILLS));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                else if(TABLE_NAME.equals(TABLE_EMPLOYERS)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_NAME, KEY_EMAIL, KEY_IMG, KEY_MOBNUM, KEY_INFO, KEY_LIKES, KEY_DISLIKES));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                else if(TABLE_NAME.equals(TABLE_LIKES)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_TYPE, KEY_LIKER, KEY_LIKEE));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }


                else if(TABLE_NAME.equals(TABLE_MATCHED)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_SEEKER, KEY_EMPLOYER));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                else if(TABLE_NAME.equals(TABLE_LISTINGS)) {
                    ArrayList<String> ColumnNames = new ArrayList<>(
                            Arrays.asList(KEY_DESC, KEY_TAGS, KEY_OWNER, KEY_SKILLSREQ, KEY_JOBTITLE, KEY_EXPREQ));

                    for(String column:ColumnNames){
                        if(column.equals(KEY_LIKES) || column.equals(KEY_DISLIKES))
                            values.put(column, jsonObject.getJSONArray(column).toString());
                        else
                            values.put(column, jsonObject.getString(column));
                    }
                }

                db.insert(TABLE_NAME, null, values);
                updateLastModified(TABLE_NAME);
            }

        } catch(JSONException e){
            Log.e(TAG, "storeEmployers: ", e);
        }
    }

    public void updateLastModified(String TABLE_NAME){
        long now = System.currentTimeMillis();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("LAST_MODIFIED", String.valueOf(now));
        String where = "TABLE_NAME = ?";
        String[] whereArgs = new String[] {TABLE_NAME};

        db.update(TABLE_LASTMODIFIED, values, where, whereArgs);
    }

    public ArrayList<SeekerClass> getSeekers(){
        ArrayList<SeekerClass> result = new ArrayList<>();

        String SELECT_QUERY = "SELECT * FROM Seekers;";
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(SELECT_QUERY, null){
            
        };

        return result;
    }

    public ArrayList<JobListingClass> getListings(){
        ArrayList<JobListingClass> result = new ArrayList<>();

        return result;
    }

    public ArrayList<EmployerClass> getEmployers(){
        ArrayList<EmployerClass> result = new ArrayList<>();

        return result;
    }
}
